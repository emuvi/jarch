package br.com.pointel.jarch.mage;

import java.time.LocalDateTime;

public class WizLocalDateTime {

    private WizLocalDateTime() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), LocalDateTime.class)
                || value instanceof java.time.LocalDate
                || value instanceof java.time.LocalTime
                || value instanceof java.time.LocalDateTime
                || value instanceof java.time.ZonedDateTime
                || value instanceof java.time.OffsetDateTime
                || value instanceof java.time.OffsetTime
                || value instanceof java.time.Instant
                || value instanceof java.util.Date
                || value instanceof java.sql.Date
                || value instanceof java.sql.Time
                || value instanceof java.sql.Timestamp
                || value instanceof String
                || value instanceof Number;
    }

    public static LocalDateTime get(Object value) throws Exception {
        if (value == null) {
            return null;
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value);
        }
        if (value instanceof java.time.LocalDate localDate) {
            return localDate.atStartOfDay();
        } else if (value instanceof java.time.LocalTime localTime) {
            return localTime.atDate(java.time.LocalDate.now());
        } else if (value instanceof java.time.LocalDateTime localDateTime) {
            return localDateTime;
        } else if (value instanceof java.time.ZonedDateTime zonedDateTime) {
            return zonedDateTime.toLocalDateTime();
        } else if (value instanceof java.time.OffsetDateTime offsetDateTime) {
            return offsetDateTime.toLocalDateTime();
        } else if (value instanceof java.time.OffsetTime offsetTime) {
            java.time.LocalDate today = java.time.LocalDate.now();
            return offsetTime.atDate(today).toLocalDateTime();
        } else if (value instanceof java.time.Instant instant) {
            return instant.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        } else if (value instanceof java.util.Date date) {
            return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        } else if (value instanceof java.sql.Date date) {
            return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        } else if (value instanceof java.sql.Time time) {
            return time.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        } else if (value instanceof java.sql.Timestamp timestamp) {
            return timestamp.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        } else if (value instanceof String formatted) {
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(formatted, format)) {
                    return format.parse(formatted, LocalDateTime::from);
                }
            }
        } else if (value instanceof Number number) {
            return java.time.Instant.ofEpochMilli(number.longValue()).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        }
        throw new Exception("Could not convert to an Instant value the value of class: " + value.getClass().getName());
    }

}
