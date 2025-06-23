package com.swpteam.smokingcessation.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtil {

    public final String DATE_TIME_FORMAT_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    public final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);

    private final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : null;
    }

    public String formatDate(LocalDateTime dateTime) {
        return dateTime != null? dateTime.format(DATE_FORMATTER) : null;
    }

    public LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            assert dateTimeStr != null;
            return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } else {
            return null;
        }
    }

    public boolean isAfter(LocalDateTime date1, LocalDateTime date2) {
        return date1 != null && date2 != null && date1.isAfter(date2);
    }

    public boolean isBefore(LocalDateTime date1, LocalDateTime date2) {
        return date1 != null && date2 != null && date1.isBefore(date2);
    }
}
