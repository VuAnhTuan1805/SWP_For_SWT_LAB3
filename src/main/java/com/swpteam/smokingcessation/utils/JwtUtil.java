package com.swpteam.smokingcessation.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.exception.AppException;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUtil {

//    private static final String ADMIN_AUDIENCE = "admin-portal";
//    private static final String WEBSITE_AUDIENCE = "web-app";

    @Value("${jwt.signer-key}")
    String SIGNER_KEY;

    @Value("${jwt.access-token-duration}")
    long ACCESS_TOKEN_DURATION;

    @Value("${jwt.refresh-token-duration}")
    long REFRESH_TOKEN_DURATION;

    @Value("${jwt.password-reset-token-duration}")
    long RESET_EMAIL_DURATION;

    JWSSigner signer;
    JWSVerifier verifier;

    @PostConstruct
    public void init() {
        try {
            signer = new MACSigner(SIGNER_KEY);
            verifier = new MACVerifier(SIGNER_KEY);
        } catch (KeyLengthException e) {
            throw new IllegalArgumentException("Invalid secret key length", e);
        } catch (JOSEException e) {
            throw new RuntimeException("Failed to initialize JWT signer/verifier", e);
        }

        log.info("{}, {}, {}, {}", SIGNER_KEY, ACCESS_TOKEN_DURATION, REFRESH_TOKEN_DURATION, RESET_EMAIL_DURATION);
    }

    public String generateAccessToken(Account account) {
        return generateToken(account, ACCESS_TOKEN_DURATION, "access_token");
    }

    public String generateRefreshToken(Account account) {
        return generateToken(account, REFRESH_TOKEN_DURATION, "refresh_token");
    }

    public String generateResetEmailToken(Account account) {
        return generateToken(account, RESET_EMAIL_DURATION, "reset_email_token");
    }

    private String generateToken(Account account, long expirySeconds, String tokenType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirySeconds * 1000);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(account.getId())
                .issuer("smoking-cessation")
                .issueTime(now)
                .expirationTime(expiryDate)
                .claim("token_type", tokenType)
                .jwtID(UUID.randomUUID().toString())
                .build();

        try {
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.TOKEN_CREATE_FAILED);
        }
    }

    public SignedJWT verifyToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            if (!jwt.verify(verifier)) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
            if (jwt.getJWTClaimsSet().getExpirationTime().before(new Date())) {
                throw new AppException(ErrorCode.TOKEN_EXPIRED);
            }
            return jwt;
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String getSubject(SignedJWT jwt) {
        try {
            return jwt.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String getJti(SignedJWT jwt) {
        try {
            return jwt.getJWTClaimsSet().getJWTID();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public Date getExpiration(SignedJWT jwt) {
        try {
            return jwt.getJWTClaimsSet().getExpirationTime();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }

    public Object getClaim(SignedJWT jwt, String claimKey) {
        try {
            return jwt.getJWTClaimsSet().getClaim(claimKey);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}
