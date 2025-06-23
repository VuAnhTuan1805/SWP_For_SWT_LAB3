package com.swpteam.smokingcessation.controller.v1.identity;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.auth.request.*;
import com.swpteam.smokingcessation.domain.dto.auth.response.AuthenticationResponse;
import com.swpteam.smokingcessation.domain.dto.auth.response.GoogleTokenResponse;
import com.swpteam.smokingcessation.service.interfaces.identity.IAuthenticationService;
import com.swpteam.smokingcessation.utils.CookieUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication", description = "Manage authentication-related operations")
public class AuthenticationController {

    IAuthenticationService authenticationService;

    @PostMapping("/google/login")
    ResponseEntity<ApiResponse<GoogleTokenResponse>> getGoogleToken(@RequestBody GoogleTokenRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<GoogleTokenResponse>builder()
                        .code(SuccessCode.GOOGLE_LOGIN_SUCCESS.getCode())
                        .message(SuccessCode.GOOGLE_LOGIN_SUCCESS.getMessage())
                        .result(authenticationService.getGoogleToken(request))
                        .build());
    }

    @PostMapping("/login")
    ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody @Valid AuthenticationRequest request, HttpServletResponse response) {
        AuthenticationResponse authenticationResponse = authenticationService.login(request);

        CookieUtil.setRefreshTokenCookie(response, authenticationResponse.getRefreshToken());
        authenticationResponse.setRefreshToken(null);
        return ResponseEntity.ok()
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .code(SuccessCode.LOGIN_SUCCESS.getCode())
                        .message(SuccessCode.LOGIN_SUCCESS.getMessage())
                        .result(authenticationResponse)
                        .build());
    }

    @PostMapping("/refresh-token")
    ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@CookieValue("refreshToken") String refreshToken, HttpServletResponse response) {
        AuthenticationResponse authenticationResponse = authenticationService.refreshToken(refreshToken);

        CookieUtil.setRefreshTokenCookie(response, authenticationResponse.getRefreshToken());
        authenticationResponse.setRefreshToken(null);
        return ResponseEntity.ok()
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .code(SuccessCode.TOKEN_REFRESH_SUCCESS.getCode())
                        .message(SuccessCode.TOKEN_REFRESH_SUCCESS.getMessage())
                        .result(authenticationResponse)
                        .build());
    }

    @PostMapping("/register")
    ResponseEntity<ApiResponse<AuthenticationResponse>> register(@RequestBody @Valid RegisterRequest request, HttpServletResponse response) {
        AuthenticationResponse authenticationResponse = authenticationService.register(request);

        CookieUtil.setRefreshTokenCookie(response, authenticationResponse.getRefreshToken());
        authenticationResponse.setRefreshToken(null);
        return ResponseEntity.ok(
                ApiResponse.<AuthenticationResponse>builder()
                        .code(SuccessCode.REGISTER_SUCCESS.getCode())
                        .message(SuccessCode.REGISTER_SUCCESS.getMessage())
                        .result(authenticationResponse)
                        .build());
    }

    @PostMapping("/forgot-password")
    ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        authenticationService.sendResetPasswordEmail(request.getEmail());
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(SuccessCode.SEND_MAIL_SUCCESS.getCode())
                        .message(SuccessCode.SEND_MAIL_SUCCESS.getMessage())
                        .result("Reset password link sent to your email if it exists in our system.")
                        .build());
    }

    @PostMapping("/reset-password")
    ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        authenticationService.resetPassword(request);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.PASSWORD_RESET_SUCCESS.getCode())
                        .message(SuccessCode.PASSWORD_RESET_SUCCESS.getMessage())
                        .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        authenticationService.logout();

        CookieUtil.clearRefreshTokenCookie(response);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.LOGOUT_SUCCESS.getCode())
                        .message(SuccessCode.LOGOUT_SUCCESS.getMessage())
                        .build()
        );
    }

}
