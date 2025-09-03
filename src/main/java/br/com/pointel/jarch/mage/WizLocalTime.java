package br.com.pointel.jarch.mage;

import java.time.LocalTime;

public class WizLocalTime {

    private WizLocalTime() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), LocalTime.class)
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

    public static LocalTime get(Object value) throws Exception {
        if (value == null) {
            return null;
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value);
        }
        if (value instanceof java.time.LocalDate localDate) {
            return localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toLocalTime();
        } else if (value instanceof java.time.LocalTime localTime) {
            return localTime;
        } else if (value instanceof java.time.LocalDateTime localDateTime) {
            return localDateTime.toLocalTime();
        } else if (value instanceof java.time.ZonedDateTime zonedDateTime) {
            return zonedDateTime.toLocalTime();
        } else if (value instanceof java.time.OffsetDateTime offsetDateTime) {
            return offsetDateTime.toLocalTime();
        } else if (value instanceof java.time.OffsetTime offsetTime) {
            return offsetTime.toLocalTime();
        } else if (value instanceof java.time.Instant instant) {
            return instant.atZone(java.time.ZoneId.systemDefault()).toLocalTime();
        } else if (value instanceof java.util.Date date) {
            return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
        } else if (value instanceof java.sql.Date date) {
            return date.toLocalDate().atStartOfDay().toLocalTime();
        } else if (value instanceof java.sql.Time time) {
            return time.toLocalTime();
        } else if (value instanceof java.sql.Timestamp timestamp) {
            return timestamp.toLocalDateTime().toLocalTime();
        } else if (value instanceof String formatted) {
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(formatted, format)) {
                    return format.parse(formatted, LocalTime::from);
                }
            }
        } else if (value instanceof Number number) {
            return java.time.Instant.ofEpochMilli(number.longValue()).atZone(java.time.ZoneId.systemDefault()).toLocalTime();
        }
        throw new Exception("Could not convert to an Instant value the value of class: " + value.getClass().getName());
    }

}
