package com.swpteam.smokingcessation.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SuccessCode {

    // Membership
    MEMBERSHIP_CREATED(1000, "Membership has been created"),
    MEMBERSHIP_UPDATED(1001, "Membership has been updated"),
    MEMBERSHIP_DELETED(1002, "Membership has been deleted"),
    MEMBERSHIP_GET_ALL(1003, "Success returning a page of membership"),
    MEMBERSHIP_GET_BY_ID(1004, "Success returning a membership with given id"),

    // Subscription
    SUBSCRIPTION_CREATED(1000, "Subscription has been created"),
    SUBSCRIPTION_UPDATED(1001, "Subscription has been updated"),
    SUBSCRIPTION_DELETED(1002, "Subscription has been deleted"),
    SUBSCRIPTION_GET_ALL(1003, "Success returning a page of subscription"),
    SUBSCRIPTION_GET_BY_ID(1004, "Success returning a subscription with given id"),
    SUBSCRIPTION_GET_BY_ACCOUNT(1004, "Success returning a subscription with given account id"),

    // Health
    HEALTH_CREATED(2000, "Health record has been created"),
    HEALTH_UPDATED(2001, "Health record has been updated"),
    HEALTH_DELETED(2002, "Health record has been deleted"),
    HEALTH_GET_ALL(1003, "Success returning a page of health"),
    HEALTH_GET_BY_ID(1004, "Success returning a health with given id"),
    HEALTH_GET_BY_ACCOUNT(1004, "Success returning a health with given account id"),

    // Record
    RECORD_CREATED(3000, "Record has been created"),
    RECORD_UPDATED(3001, "Record has been updated"),
    RECORD_DELETED(3002, "Record has been deleted"),
    RECORD_GET_ALL(1003, "Success returning a page of record"),
    RECORD_GET_BY_ID(1004, "Success returning a record with given id"),
    RECORD_GET_BY_ACCOUNT(1004, "Success returning a record with given account id"),

    // Message
    MESSAGE_CREATED(2000, "Message has been created"),
    MESSAGE_UPDATED(2001, "Message has been updated"),
    MESSAGE_DELETED(2002, "Message has been deleted"),
    MESSAGE_GET_BY_ID(2003, "Success returning a membership with given id"),

    // Plan
    PLAN_CREATED(5000, "Plan has been created"),
    PLAN_UPDATED(5001, "Plan has been updated"),
    PLAN_DELETED(5002, "Plan has been deleted"),
    PLAN_GET_BY_ID(5003, "Success returning a plan with given id"),
    PLAN_GET_ALL(5004, "Success return a page with plan"),
    PLAN_TEMPLATE_GET(4324, "Success return plan template"),

    // Phase
    PHASE_CREATED(5000, "Phase has been created"),
    PHASE_UPDATED(5001, "Phase has been updated"),
    PHASE_DELETED(5002, "Phase has been deleted"),
    PHASE_GET_BY_ID(5003, "Success returning a phase with given id"),
    PHASE_GET_ALL(5004, "Success return a page with phase"),

    // Account
    ACCOUNT_CREATED(3000, "Account has been created"),
    ACCOUNT_UPDATED(3001, "Account has been updated"),
    ACCOUNT_DELETED(3002, "Account has been deleted"),
    ACCOUNT_BANNED(3003, "Account has been banned"),
    PASSWORD_CHANGE_SUCCESS(3004, "Password is changed successfully"),
    ROLE_UPDATED(3005, "Account role is updated successfully"),
    GET_ME(3006, "Fetched the current account successfully"),

    //Authentication
    GOOGLE_LOGIN_SUCCESS(4000, "Google log in successfully"),
    LOGIN_SUCCESS(4001, "Logged in successfully"),
    REGISTER_SUCCESS(4002, "Registered successfully"),
    PASSWORD_RESET_SUCCESS(4003, "Password reset successfully"),
    TOKEN_REFRESH_SUCCESS(4004, "Issued a new access token successfully"),
    LOGOUT_SUCCESS(4005, "Logged out successfully"),

    // Coach
    COACH_CREATED(5000, "Coach has been created"),
    COACH_UPDATED(5001, "Coach has been updated"),
    COACH_DELETED(5002, "Coach has been deleted"),
    COACH_GET_BY_ID(5003, "Success returning a coach with given id"),
    COACH_GET_ALL(5004, "Success return a page with coach"),

    // Member
    MEMBER_CREATED(3000, "Member has been created"),
    MEMBER_UPDATED(3001, "Member has been updated"),

    // Mail
    SEND_MAIL_SUCCESS(1004, "Success sending mail"),

    // Booking
    BOOKING_GET_ALL(2010, "Successfully retrieved all categories"),
    BOOKING_GET_BY_ID(2011, "Successfully retrieved booking by ID"),
    BOOKING_CREATED(2012, "Booking created successfully"),
    BOOKING_UPDATED(2013, "Booking updated successfully"),
    BOOKING_DELETED(2014, "Booking deleted successfully"),

    // Chatbot
    RETURN_MESSAGE(1000, "Success returning response"),

    // Cloudinary Image Upload
    UPLOAD_IMAGE(1000, "Success uploading image"),

    // Blog
    BLOG_GET_ALL(2010, "Successfully retrieved blog page"),
    BLOG_GET_BY_ID(2011, "Successfully retrieved blog by ID"),
    BLOG_GET_BY_SLUG(2011, "Successfully retrieved blog by slug"),
    BLOG_GET_BY_CATEGORY(2011, "Successfully retrieved blog by category"),
    MY_BLOG(2011, "Successfully my blog"),
    BLOG_CREATED(2012, "Blog created successfully"),
    BLOG_UPDATED(2013, "Blog updated successfully"),
    BLOG_DELETED(2014, "Blog deleted successfully"),

    // Category
    CATEGORY_LIST_ALL(2010, "Successfully listed all categories"),
    CATEGORY_GET_ALL(2010, "Successfully retrieved all categories"),
    CATEGORY_GET_BY_ID(2011, "Successfully retrieved category by ID"),
    CATEGORY_CREATED(2012, "Category created successfully"),
    CATEGORY_UPDATED(2013, "Category updated successfully"),
    CATEGORY_DELETED(2014, "Category deleted successfully"),

    // Comment
    COMMENT_LIST_ALL(2010, "Successfully listed all comments"),
    COMMENT_GET_BY_BLOG(2010, "Successfully retrieved all comments by blog"),
    COMMENT_GET_BY_ID(2011, "Successfully retrieved comment by ID"),
    COMMENT_CREATED(2012, "Comment created successfully"),
    COMMENT_UPDATED(2013, "Comment updated successfully"),
    COMMENT_DELETED(2014, "Comment deleted successfully"),

    // Chat
    CHAT_GET_ALL(1101, "Successfully retrieved all chats"),
    CHAT_GET_BY_ID(1102, "Successfully retrieved all chats by ID"),
    CHAT_DELETED(1103, "Successfully deleted chat"),

    // Notification
    NOTIFICATION_GET_ALL(1101, "Successfully retrieved all notifications"),
    NOTIFICATION_GET_BY_ID(1102, "Successfully retrieved all notifications by ID"),
    NOTIFICATION_DELETED(1103, "Successfully deleted notification"),
    ALL_NOTIFICATION_DELETED(1103, "Successfully deleted all notifications associated with the account ID"),

    // Streak
    STREAK_LIST_ALL(2010, "Successfully listed all streaks"),
    STREAK_GET_BY_ID(2011, "Successfully retrieved streak by ID"),
    STREAK_CREATED(2012, "Streak created successfully"),
    STREAK_UPDATED(2013, "Streak updated successfully"),
    STREAK_DELETED(2014, "Streak deleted successfully"),
    STREAK_RESET(2015, "Streak reset successfully"),

    // TimeTable
    TIMETABLE_GET_ALL(200, "Successfully retrieved all timetables"),
    TIMETABLE_GET_BY_ID(200, "Successfully retrieved timetable by ID"),
    TIMETABLE_GET_BY_COACH_ID(200, "Successfully retrieved timetables by coach ID"),
    TIMETABLE_CREATED(201, "Timetable created successfully"),
    TIMETABLE_UPDATED(200, "Timetable updated successfully"),
    TIMETABLE_DELETED(200, "Timetable deleted successfully");
    ;
    int code;
    String message;
}
