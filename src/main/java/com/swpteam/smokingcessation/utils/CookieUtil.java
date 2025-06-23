package com.swpteam.smokingcessation.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CookieUtil {

    private final int REFRESH_TOKEN_EXPIRY = 60 * 60 * 24 * 7; // 7 days

    public void setRefreshTokenCookie(HttpServletResponse response, String token) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", token);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // set to true in production
        refreshTokenCookie.setPath("/api/auth/refresh-token");
        refreshTokenCookie.setMaxAge(REFRESH_TOKEN_EXPIRY);
        response.addCookie(refreshTokenCookie);
    }

    public void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", "");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // set to true in production
        refreshTokenCookie.setPath("/api/auth/refresh-token");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
    }
}
