package com.swpteam.smokingcessation.constant;

public final class App {

    public static final String[] PUBLIC_ENDPOINTS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/ws/**",
            "/api/webhook/stripe",
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/google/login",
            "/api/v1/auth/forgot-password",
            "/api/v1/test"
    };

    public static final String INIT_TEST_MEMBER_EMAIL = "member@gmail.com";
    public static final String INIT_TEST_MEMBER_PASS = "1";

    public static final String INIT_TEST_COACH_EMAIL = "coach@gmail.com";
    public static final String INIT_TEST_COACH_PASS = "1";

    public static final String DEFAULT_CATEGORY = "Uncategorized";

    private App() {}
}
