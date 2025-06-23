package com.swpteam.smokingcessation.service.interfaces.identity;

import com.nimbusds.jose.JOSEException;
import com.swpteam.smokingcessation.domain.dto.auth.request.*;
import com.swpteam.smokingcessation.domain.dto.auth.response.AuthenticationResponse;
import com.swpteam.smokingcessation.domain.dto.auth.response.GoogleTokenResponse;
import org.springframework.security.core.Authentication;

import java.text.ParseException;

public interface IAuthenticationService {

    GoogleTokenResponse getGoogleToken(GoogleTokenRequest request);

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse refreshToken(String token);

    AuthenticationResponse register(RegisterRequest request);

    Authentication authenticate(String token);

    void sendResetPasswordEmail(String email);

    void resetPassword(ResetPasswordRequest request);

    void logout();
}
