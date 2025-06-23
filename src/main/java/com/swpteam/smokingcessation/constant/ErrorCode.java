package com.swpteam.smokingcessation.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_MESSAGE_KEY(9999, "Invalid message key", HttpStatus.BAD_REQUEST),

    // Common
    ACCOUNT_REQUIRED(1000, "Account is required", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(1001, "Email is required", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT(1002, "Wrong email format", HttpStatus.BAD_REQUEST),
    START_DATE_REQUIRED(1003, "Start date is required", HttpStatus.BAD_REQUEST),
    END_DATE_REQUIRED(1004, "End date is required", HttpStatus.BAD_REQUEST),
    PAYMENT_STATUS_REQUIRED(1005, "Payment status is required", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_BLANK(1006, "Account ID cannot be blank", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(1007, "Password field cannot be empty", HttpStatus.BAD_REQUEST),
    CODE_REQUIRED(1008, "Google code field cannot be empty", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_REQUIRED(1009, "Refresh token field cannot be empty", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1010, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(1011, "Phone number must be 10 digits and consists of numbers only", HttpStatus.BAD_REQUEST),
    RESET_TOKEN_REQUIRED(1012, "Reset token cannot be empty", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1013, "Token is invalid", HttpStatus.INTERNAL_SERVER_ERROR),
    ID_REQUIRED(1007, "ID is required", HttpStatus.BAD_REQUEST),
    ID_NOT_BLANK(1008, "ID cannot be blank", HttpStatus.BAD_REQUEST),
    PAGE_NO_MIN(1009, "Page number must be at least 0", HttpStatus.BAD_REQUEST),
    PAGE_SIZE_MIN(1010, "Page size must be at least 1", HttpStatus.BAD_REQUEST),
    PAGE_SIZE_MAX(1011, "Page size can not pass 100", HttpStatus.BAD_REQUEST),
    INVALID_SORT_FIELD(1013, "Invalid sort field", HttpStatus.BAD_REQUEST),
    INVALID_RESET_TOKEN(1012, "Reset token is invalid", HttpStatus.BAD_REQUEST),
    ACCOUNT_ROLE_REQUIRED(1014, "Account role field is required", HttpStatus.BAD_REQUEST),

    // Authentication
    UNAUTHENTICATED(2000, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2001, "You do not have permission", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(2002, "Token is expired", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(2003, "Wrong password for email", HttpStatus.BAD_REQUEST),
    INVALID_SIGNATURE(2004, "Token signature is invalid", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(2005, "Failed to send email. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR),
    USED_TOKEN(2006, "Token has expired or has been used", HttpStatus.BAD_REQUEST),
    SELF_BAN(2007, "You cannot ban yourself", HttpStatus.BAD_REQUEST),
    FORBIDDEN(403, "You do not have permission to access this resource", HttpStatus.FORBIDDEN),
    TOKEN_REQUIRED(403, "Token is required", HttpStatus.FORBIDDEN),
    TOKEN_CREATE_FAILED(403, "Token is required", HttpStatus.INTERNAL_SERVER_ERROR),

    // Account
    ACCOUNT_NOT_FOUND(3000, "Account does not exist", HttpStatus.BAD_REQUEST),
    ACCOUNT_EXISTED(3001, "Account already existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(3001, "Email already existed", HttpStatus.BAD_REQUEST),
    ACCOUNT_DELETED(3002, "Account has been deleted", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTED(3003, "Phone number is registered to another account", HttpStatus.BAD_REQUEST),
    IDENTICAL_PASSWORD(3004, "The new password must be different from the old password", HttpStatus.BAD_REQUEST),
    ROLE_REQUIRED(3004, "Role is required", HttpStatus.BAD_REQUEST),

    // Membership
    MEMBERSHIP_NOT_FOUND(4000, "Membership does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    MEMBERSHIP_NAME_UNIQUE(4001, "Membership name must be unique", HttpStatus.BAD_REQUEST),
    MEMBERSHIP_NAME_REQUIRE(4002, "Membership name must not be empty", HttpStatus.BAD_REQUEST),
    MEMBERSHIP_MIN_SIZE(4003, "Membership name length must be at least {min} characters", HttpStatus.BAD_REQUEST),
    DURATION_NEGATIVE(4004, "Duration must be a positive number", HttpStatus.BAD_REQUEST),
    PRICE_NEGATIVE(4005, "Price must be a positive number", HttpStatus.BAD_REQUEST),
    DURATION_REQUIRED(4006, "Duration is required", HttpStatus.BAD_REQUEST),
    PRICE_REQUIRED(4007, "Price is required", HttpStatus.BAD_REQUEST),

    // Transaction
    AMOUNT_REQUIRED(5001, "Transaction amount is required", HttpStatus.BAD_REQUEST),
    NAME_REQUIRED(5002, "Name is required", HttpStatus.BAD_REQUEST),
    CURRENCY_REQUIRED(5003, "Currency is required", HttpStatus.BAD_REQUEST),
    AMOUNT_NEGATIVE(5004, "Transaction amount must be a positive number", HttpStatus.BAD_REQUEST),

    // Subscription
    SUBSCRIPTION_NOT_FOUND(4000, "Subscription does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    START_DATE_MUST_BE_TODAY_OR_FUTURE(6001, "Start date must be today or in the future", HttpStatus.BAD_REQUEST),
    END_DATE_MUST_BE_IN_FUTURE(6002, "End date must be today or in the future", HttpStatus.BAD_REQUEST),

    // Setting
    THEME_REQUIRED(7000, "Theme is required", HttpStatus.BAD_REQUEST),
    LANGUAGE_REQUIRED(7001, "Language is required", HttpStatus.BAD_REQUEST),
    TRACKING_MODE_REQUIRED(7002, "Tracking mode is required", HttpStatus.BAD_REQUEST),
    MOTIVATION_REQUIRED(7003, "Motivation per day is required", HttpStatus.BAD_REQUEST),
    MOTIVATION_MIN(7004, "Motivation per day must be at least 1", HttpStatus.BAD_REQUEST),
    MOTIVATION_MAX(7005, "Motivation per day must be at most 100", HttpStatus.BAD_REQUEST),
    DEADLINE_REQUIRED(7006, "Report deadline is required", HttpStatus.BAD_REQUEST),

    //Member
    MEMBER_EXISTED(8000, "Member fields already exist", HttpStatus.BAD_REQUEST),
    MEMBER_NOT_FOUND(8001, "Member doesn't exist", HttpStatus.BAD_REQUEST),
    GENDER_REQUIRED(8001, "Gender is required", HttpStatus.BAD_REQUEST),

    // Message
    MESSAGE_NOT_FOUND(8001, "Message does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    MESSAGE_CONTENT_REQUIRED(8002, "Message content is required", HttpStatus.BAD_REQUEST),

    // Health
    HEALTH_RECORD_NOT_FOUND(4000, "Health record does not exist", HttpStatus.NOT_FOUND),
    CIGARETTES_PER_DAY_INVALID(4002, "Cigarettes per day must be non-negative", HttpStatus.BAD_REQUEST),
    CIGARETTES_PER_PACK_INVALID(4003, "Cigarettes per pack must be non-negative", HttpStatus.BAD_REQUEST),
    FND_LEVEL_INVALID_MIN(4004, "FND level must be >= 0", HttpStatus.BAD_REQUEST),
    FND_LEVEL_INVALID_MAX(4005, "FND level must be <= 10", HttpStatus.BAD_REQUEST),
    PACK_PRICE_INVALID(4006, "Pack price must be non-negative", HttpStatus.BAD_REQUEST),
    REASON_TO_QUIT_REQUIRED(4007, "Reason to quit is required", HttpStatus.BAD_REQUEST),
    REASON_TO_QUIT_TOO_LONG(4008, "Reason to quit must not exceed 255 characters", HttpStatus.BAD_REQUEST),
    SMOKE_YEAR_INVALID(4009, "Smoke year must be non-negative", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(4001, "Access denied", HttpStatus.FORBIDDEN),
    PACK_PRICE_TOO_HIGH(4010, "Pack price must not exceed 500.0", HttpStatus.BAD_REQUEST),

    // Record
    RECORD_NOT_FOUND(5000, "Record does not exist", HttpStatus.NOT_FOUND),
    CIGARETTES_SMOKED_INVALID(5002, "Cigarettes smoked must be non-negative", HttpStatus.BAD_REQUEST),
    RECORD_DATE_REQUIRED(5003, "Date is required", HttpStatus.BAD_REQUEST),
    RECORD_DATE_INVALID(5004, "Date must be today or in the future", HttpStatus.BAD_REQUEST),
    RECORD_ALREADY_EXISTS(5005, "Record for this date already exists", HttpStatus.BAD_REQUEST),

    // Currency
    CURRENCY_RATE_ERROR(8000, "Error while updating currency rates", HttpStatus.BAD_REQUEST),
    INVALID_CURRENCY(8000, "Invalid currency", HttpStatus.BAD_REQUEST),

    // Transaction
    TRANSACTION_NOT_FOUND(4000, "Transaction does not exist or have been deleted", HttpStatus.NOT_FOUND),

    // Plan
    PLAN_NOT_FOUND(9000, "Plan does not exist", HttpStatus.BAD_REQUEST),
    PLAN_START_DATE_MUST_BE_TODAY_OR_FUTURE(9001, "Start date must be today or in the future", HttpStatus.BAD_REQUEST),
    PLAN_END_DATE_MUST_BE_IN_FUTURE(9002, "End date must in the future", HttpStatus.BAD_REQUEST),
    PLAN_START_DATE_REQUIRED(1013, "start date required", HttpStatus.BAD_REQUEST),
    PLAN_END_DATE_REQUIRED(1014, "end date required", HttpStatus.BAD_REQUEST),
    PLAN_STATUS_REQUIRED(1015, "Plan status is required", HttpStatus.BAD_REQUEST),
    ACCOUNT_ID_REQUIRED(2133, "Account id is required", HttpStatus.BAD_REQUEST),
    PLAN_NAME_REQUIRED(2143, "Plan name is required", HttpStatus.BAD_REQUEST),
    PLAN_NAME_TOO_LONG(2153, "Plan name is too long", HttpStatus.BAD_REQUEST),
    PLAN_DESCRIPTION_REQUIRED(2133, "Plan description is required", HttpStatus.BAD_REQUEST),
    PLAN_DESCRIPTION_TOO_LONG(2173, "Plan description is too long", HttpStatus.BAD_REQUEST),
    SUCCESS_RATE_REQUIRED(2183, "Success rate is required", HttpStatus.BAD_REQUEST),
    SUCCESS_LEVEL_INVALID_MIN(2183, "Success min is invalid", HttpStatus.BAD_REQUEST),
    SUCCESS_LEVEL_INVALID_MAX(2183, "Success max is invalid", HttpStatus.BAD_REQUEST),
    PLAN_ID_REQUIRED(2312, "PlanId is required", HttpStatus.BAD_REQUEST),
    INVALID_FTND_SCORE(2332, "ftnd score must greater than 0 and less than 10", HttpStatus.BAD_REQUEST),


    // Phase
    PHASE_START_DATE_REQUIRED(1023, "start date required", HttpStatus.BAD_REQUEST),
    PHASE_END_DATE_REQUIRED(1024, "end date required", HttpStatus.BAD_REQUEST),
    PHASE_NOT_FOUND(1020, "Phase does not exist", HttpStatus.BAD_REQUEST),
    PHASE_START_DATE_MUST_BE_TODAY_OR_FUTURE(1021, "Start date must be today or in the future", HttpStatus.BAD_REQUEST),
    PHASE_END_DATE_MUST_BE_IN_FUTURE(1022, "End date must be in the future", HttpStatus.BAD_REQUEST),
    PHASE_STATUS_REQUIRED(1025, "Phase status is required", HttpStatus.BAD_REQUEST),
    PHASE_DESCRIPTION_REQUIRED(1111, "Phase description is required", HttpStatus.BAD_REQUEST),
    PHASE_DESCRIPTION_TOO_LONG(1111, "Phase description is too long", HttpStatus.BAD_REQUEST),
    PHASE_CIGARETTE_BOUND_REQUIRED(1323, "Phase bound is required", HttpStatus.BAD_REQUEST),
    CIGARETTE_NEGATIVE(4005, "Cigarette bound must be a positive number", HttpStatus.BAD_REQUEST),

    // Chatbot
    PROMPT_NOT_BLANK(4000, "Prompt cannot be blank", HttpStatus.BAD_REQUEST),
    PROMPT_MAX_SIZE(4000, "Prompt must not exceed 200 characters", HttpStatus.BAD_REQUEST),
    OUT_OF_LIMIT(4000, "Out of limit of today", HttpStatus.TOO_MANY_REQUESTS),

    // Coach
    COACH_NOT_FOUND(1040, "Coach not found", HttpStatus.BAD_REQUEST),
    COACH_BIO_TOO_LONG(1030, "Coach bio too long", HttpStatus.BAD_REQUEST),
    COACH_SOCIAL_LINK_REQUIRED(1221, "Coach social link required", HttpStatus.BAD_REQUEST),
    COACH_SPECIALIZATION_REQUIRED(1232, "Coach specialization required", HttpStatus.BAD_REQUEST),
    COACH_CERTIFICATES_REQUIRED(2212, "Coach certificate required", HttpStatus.BAD_REQUEST),

    // Booking
    BOOKING_NOT_FOUND(1000, "Booking not found", HttpStatus.BAD_REQUEST),
    COACH_ACCOUNT_ID_REQUIRED(1003, "Coach account ID is required", HttpStatus.BAD_REQUEST),
    MEET_LINK_REQUIRED(1004, "Meet link is required", HttpStatus.BAD_REQUEST),
    MEET_LINK_TOO_LONG(1005, "Meet link is too long", HttpStatus.BAD_REQUEST),
    STARTED_AT_REQUIRED(1006, "Started at is required", HttpStatus.BAD_REQUEST),
    STARTED_AT_MUST_BE_TODAY_OR_FUTURE(1007, "Started at must be today or in the future", HttpStatus.BAD_REQUEST),
    ENDED_AT_REQUIRED(1008, "Ended at is required", HttpStatus.BAD_REQUEST),
    ENDED_AT_MUST_BE_IN_FUTURE(1009, "Ended at must be in the future", HttpStatus.BAD_REQUEST),
    IS_APPROVED_REQUIRED(1010, "Approval status is required", HttpStatus.BAD_REQUEST),

    // Category
    CATEGORY_NAME_REQUIRED(4000, "Category name must not be blank", HttpStatus.BAD_REQUEST),
    CATEGORY_MAX_LENGTH(4000, "Category name must not exceed 100 characters", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(4000, "Category does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    CATEGORY_CANNOT_BE_DELETED(4000, "This category can not be deleted", HttpStatus.BAD_REQUEST),

    // Comment
    COMMENT_CONTENT_REQUIRED(4000, "Content must not be blank", HttpStatus.BAD_REQUEST),
    COMMENT_REPLY_REQUIRED(4000, "Content reply is required", HttpStatus.BAD_REQUEST),
    LEVEL_POSITIVE(4000, "Level must be zero or positive", HttpStatus.BAD_REQUEST),
    COMMENT_BLOG_REQUIRED(4000, "Blog ID must not be blank", HttpStatus.BAD_REQUEST),
    COMMENT_USER_REQUIRED(4000, "User ID must not be blank", HttpStatus.BAD_REQUEST),
    OTHERS_COMMENT_UNCHANGEABLE(4000, "Can not change other user comment", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_FOUND(4000, "Comment does not exist or have been deleted", HttpStatus.BAD_REQUEST),

    // Blog
    BLOG_TITLE_REQUIRED(4000, "Title is required", HttpStatus.BAD_REQUEST),
    BLOG_TITLE_LIMIT(4000, "Title must not exceed 255 characters", HttpStatus.BAD_REQUEST),
    BLOG_SLUG_REQUIRED(4000, "Slug is required", HttpStatus.BAD_REQUEST),
    SLUG_PATTERN(4000, "Slug must be lowercase and can contain hyphens", HttpStatus.BAD_REQUEST),
    SLUG_LENGTH_LIMIT(4000, "Slug must not exceed 255 characters", HttpStatus.BAD_REQUEST),
    COVER_IMAGE_LENGTH_LIMIT(4000, "Cover image URL must not exceed 1000 characters", HttpStatus.BAD_REQUEST),
    COVER_IMAGE_INVALID_URL(4000, "Cover image URL must be a valid URL", HttpStatus.BAD_REQUEST),
    EXCERPT_LENGTH_LIMIT(4000, "Excerpt must not exceed 500 characters", HttpStatus.BAD_REQUEST),
    BLOG_STATUS_REQUIRED(4000, "Status is required", HttpStatus.BAD_REQUEST),
    BLOG_NOT_FOUND(4000, "Blog does not exist or have been deleted", HttpStatus.BAD_REQUEST),

    // Google calender
    GOOGLE_CALENDAR_ERROR(2131, "google api error", HttpStatus.BAD_REQUEST),

    // Chat
    CHAT_MESSAGE_REQUIRED(1100, "Chat message is required", HttpStatus.BAD_REQUEST),
    CHAT_SIZE_EXCEED(1101, "Chat message needs to be under {max} characters", HttpStatus.BAD_REQUEST),
    OTHERS_CHAT_CANNOT_BE_DELETED(1102, "Other's chat message cannot be deleted", HttpStatus.FORBIDDEN),
    CHAT_NOT_FOUND(1103, "Chat does not exist or have been deleted", HttpStatus.BAD_REQUEST),

    // Notification
    OTHERS_NOTIFICATION_CANNOT_BE_DELETED(2100, "Other's notifications cannot be deleted", HttpStatus.FORBIDDEN),
    NOTIFICATION_CONTENT_REQUIRED(2101, "Content for notification is required", HttpStatus.BAD_REQUEST),
    NOTIFICATION_ID_REQUIRED(2102, "Notification ID is required to mark as read", HttpStatus.BAD_REQUEST),
    NOTIFICATION_NOT_FOUND(2103, "Notification does not exist or have been deleted", HttpStatus.BAD_REQUEST),

    // Streak
    STREAK_RESET_FAILED(3100, "Failure in resetting the streak", HttpStatus.INTERNAL_SERVER_ERROR),
    STREAK_NOT_FOUND(3101, "Streak does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    STREAK_INVALID(3102, "Streak must be a non-negative number", HttpStatus.BAD_REQUEST),
    STREAK_REQUIRED(3103, "Streak field must be entered", HttpStatus.BAD_REQUEST),
    OTHERS_STREAK_CANNOT_BE_DELETED(3104, "Other's streak counter cannot be deleted", HttpStatus.FORBIDDEN),
    STREAK_ALREADY_EXISTS(3105, "Streak already exists in the system", HttpStatus.CONFLICT),

    // Report
    PAST_FROM_DATE(4100, "The from date needs to be in the past", HttpStatus.BAD_REQUEST),
    PAST_TO_DATE(4101, "The to date needs to be in the past or present", HttpStatus.BAD_REQUEST),
    FROM_DATE_REQUIRED(4102, "The from date cannot be empty", HttpStatus.BAD_REQUEST),

    // Achievement
    ACHIEVEMENT_NOT_FOUND(6000, "Achievement does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    ACHIEVEMENT_ALREADY_EXISTS(6001, "Achievement already exists", HttpStatus.BAD_REQUEST),
    ACHIEVEMENT_NAME_REQUIRED(6002, "Achievement name is required", HttpStatus.BAD_REQUEST),
    ACHIEVEMENT_DESCRIPTION_REQUIRED(6003, "Achievement description is required", HttpStatus.BAD_REQUEST),
    ACHIEVEMENT_ICON_URL_REQUIRED(6004, "Achievement icon URL is required", HttpStatus.BAD_REQUEST),
    ACHIEVEMENT_CRITERIA_TYPE_REQUIRED(6005, "Achievement criteria type is required", HttpStatus.BAD_REQUEST),
    ACHIEVEMENT_CRITERIA_VALUE_REQUIRED(6006, "Achievement criteria value is required", HttpStatus.BAD_REQUEST),
    ACHIEVEMENT_CRITERIA_VALUE_INVALID(6007, "Achievement criteria value must be positive", HttpStatus.BAD_REQUEST),

    // Feedback
    FEEDBACK_NOT_FOUND(7000, "Feedback does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    FEEDBACK_COMMENT_REQUIRED(7001, "Feedback comment is required", HttpStatus.BAD_REQUEST),
    FEEDBACK_RATING_MIN(7002, "Feedback rating must be at least 1", HttpStatus.BAD_REQUEST),
    FEEDBACK_RATING_MAX(7003, "Feedback rating must be at most 5", HttpStatus.BAD_REQUEST),

    // Review
    REVIEW_NOT_FOUND(8000, "Review does not exist or have been deleted", HttpStatus.BAD_REQUEST),
    REVIEW_RATING_MIN(8001, "Review rating must be at least 1", HttpStatus.BAD_REQUEST),
    REVIEW_RATING_MAX(8002, "Review rating must be at most 5", HttpStatus.BAD_REQUEST),
    REVIEW_TYPE_REQUIRED(8003, "Review type is required", HttpStatus.BAD_REQUEST),
    REVIEW_COACH_ID_REQUIRED(8004, "Coach ID is required", HttpStatus.BAD_REQUEST),
    ;
    int code;
    String message;
    HttpStatusCode httpCode;
}
