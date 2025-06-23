package com.swpteam.smokingcessation.service.impl.identity;

import com.nimbusds.jwt.SignedJWT;
import com.swpteam.smokingcessation.domain.dto.auth.request.*;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.mapper.AccountMapper;
import com.swpteam.smokingcessation.integration.mail.IMailService;
import com.swpteam.smokingcessation.repository.AccountRepository;
import com.swpteam.smokingcessation.domain.dto.account.AccountResponse;
import com.swpteam.smokingcessation.domain.enums.AccountStatus;
import com.swpteam.smokingcessation.domain.enums.Role;
import com.swpteam.smokingcessation.domain.dto.auth.response.AuthenticationResponse;
import com.swpteam.smokingcessation.domain.dto.auth.response.GoogleTokenResponse;
import com.swpteam.smokingcessation.repository.RefreshTokenRepository;
import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.RefreshToken;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.security.UserPrincipal;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import com.swpteam.smokingcessation.service.interfaces.identity.IAuthenticationService;
import com.swpteam.smokingcessation.utils.AuthUtil;
import com.swpteam.smokingcessation.utils.JwtUtil;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements IAuthenticationService {

    AccountMapper accountMapper;
    AccountRepository accountRepository;
    IAccountService accountService;
    RefreshTokenRepository refreshTokenRepository;

    WebClient webClient = WebClient.create();
    PasswordEncoder passwordEncoder;

    IMailService mailService;
    AuthUtil authUtil;
    JwtUtil jwtUtil;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri:http://localhost:8080/oauth2/callback}")
    protected String REDIRECT_URI;

    @NonFinal
    @Value("${app.frontend-domain}")
    protected String FRONTEND_DOMAIN;

    @Override
    public GoogleTokenResponse getGoogleToken(@NotNull GoogleTokenRequest request) {
        String tokenEndpoint = "https://oauth2.googleapis.com/token";
        Mono<GoogleTokenResponse> responseMono = webClient.post()
                .uri(tokenEndpoint)
                .bodyValue(
                        "code=" + request.getCode() +
                                "&client_id=" + CLIENT_ID +
                                "&client_secret=" + CLIENT_SECRET +
                                "&redirect_uri=" + REDIRECT_URI +
                                "&grant_type=authorization_code"
                )
                .header("Content-Type", "application/x-www-form-urlencoded")
                .retrieve()
                .bodyToMono(GoogleTokenResponse.class);

        return responseMono.block();
    }

    @Override
    public AuthenticationResponse login(@NotNull AuthenticationRequest request) {
        Account account = accountService.findAccountByEmail(request.getEmail());

        boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }

        AccountResponse accountResponse = accountMapper.toResponse(account);
        String accessToken = jwtUtil.generateAccessToken(account);
        String refreshToken = jwtUtil.generateRefreshToken(account);

        return AuthenticationResponse.builder()
                .accountResponse(accountResponse)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse refreshToken(String token) {
        SignedJWT jwt = jwtUtil.verifyToken(token);

        String jti = jwtUtil.getJti(jwt);
        if (refreshTokenRepository.existsById(jti)) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        refreshTokenRepository.save(RefreshToken.builder()
                .id(jti)
                .expiryTime(jwtUtil.getExpiration(jwt))
                .accountId(jwtUtil.getSubject(jwt))
                .build());

        Account account = accountService.findAccountById(jwtUtil.getSubject(jwt));

        String newAccessToken = jwtUtil.generateAccessToken(account);
        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .accountResponse(accountMapper.toResponse(account))
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse register(@NotNull RegisterRequest request) {
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.ACCOUNT_EXISTED);
        }

        Account account = accountMapper.toEntityFromRegister(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRole(Role.MEMBER);
        account.setStatus(AccountStatus.ONLINE);
        account.setSetting(Setting.getDefaultSetting(account));

        account = accountRepository.save(account);

        return AuthenticationResponse.builder()
                .accessToken(jwtUtil.generateAccessToken(account))
                .refreshToken(jwtUtil.generateRefreshToken(account))
                .accountResponse(accountMapper.toResponse(account))
                .build();
    }

    @Override
    public Authentication authenticate(String token) {
        SignedJWT jwt = jwtUtil.verifyToken(token);
        Account account = accountService.findAccountById(jwtUtil.getSubject(jwt));

        UserPrincipal principal = UserPrincipal.builder().account(account).build();
        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }

    @Override
    public void sendResetPasswordEmail(String email) {
        Account account = accountService.findAccountByEmail(email);

        String token = jwtUtil.generateResetEmailToken(account);
        String link = FRONTEND_DOMAIN + "/reset-password?token=" + token;

        try {
            mailService.sendResetPasswordEmail(email, link, account.getEmail());
        } catch (MessagingException e) {
            log.error("Send email failed", e);
            throw new AppException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        try {
            SignedJWT jwt = jwtUtil.verifyToken(request.getToken());
            if (!"reset_email_token".equals(jwt.getJWTClaimsSet().getClaim("token_type"))) {
                throw new AppException(ErrorCode.INVALID_RESET_TOKEN);
            }

            String jti = jwt.getJWTClaimsSet().getJWTID();
            Date exp = jwt.getJWTClaimsSet().getExpirationTime();
            if (exp.before(new Date()) || refreshTokenRepository.existsById(jti)) {
                throw new AppException(ErrorCode.TOKEN_EXPIRED);
            }

            String accountId = jwt.getJWTClaimsSet().getSubject();
            Account account = accountService.findAccountById(accountId);
            account.setPassword(passwordEncoder.encode(request.getNewPassword()));
            accountRepository.save(account);

            refreshTokenRepository.save(
                    RefreshToken.builder()
                            .id(jti)
                            .accountId(accountId)
                            .expiryTime(exp)
                            .build()
            );
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_RESET_TOKEN);
        }
    }

    @Override
    @Transactional
    public void logout() {
        String token = authUtil.getCurrentToken()
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        SignedJWT jwt = jwtUtil.verifyToken(token);
        String jti = jwtUtil.getJti(jwt);
        Date expiry = jwtUtil.getExpiration(jwt);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .id(jti)
                        .accountId(jwtUtil.getSubject(jwt))
                        .expiryTime(expiry)
                        .build()
        );
    }
    
}
