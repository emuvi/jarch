package br.com.pointel.jarch.mage;

import java.time.ZonedDateTime;

public class WizZonedDateTime {

    private WizZonedDateTime() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), ZonedDateTime.class)
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

    public static ZonedDateTime get(Object value) throws Exception {
        if (value == null) {
            return null;
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value);
        }
        if (value instanceof java.time.LocalDate localDate) {
            return localDate.atStartOfDay(java.time.ZoneId.systemDefault());
        } else if (value instanceof java.time.LocalTime localTime) {
            return localTime.atDate(java.time.LocalDate.now()).atZone(java.time.ZoneId.systemDefault());
        } else if (value instanceof java.time.LocalDateTime localDateTime) {
            return localDateTime.atZone(java.time.ZoneId.systemDefault());
        } else if (value instanceof java.time.ZonedDateTime zonedDateTime) {
            return zonedDateTime;
        } else if (value instanceof java.time.OffsetDateTime offsetDateTime) {
            return offsetDateTime.toZonedDateTime();
        } else if (value instanceof java.time.OffsetTime offsetTime) {
            return offsetTime.atDate(java.time.LocalDate.now()).atZoneSameInstant(java.time.ZoneId.systemDefault());
        } else if (value instanceof java.time.Instant instant) {
            return instant.atZone(java.time.ZoneId.systemDefault());
        } else if (value instanceof java.util.Date date) {
            return date.toInstant().atZone(java.time.ZoneId.systemDefault());
        } else if (value instanceof java.sql.Date date) {
            return date.toInstant().atZone(java.time.ZoneId.systemDefault());
        } else if (value instanceof java.sql.Time time) {
            return time.toInstant().atZone(java.time.ZoneId.systemDefault());
        } else if (value instanceof java.sql.Timestamp timestamp) {
            return timestamp.toInstant().atZone(java.time.ZoneId.systemDefault());
        } else if (value instanceof String formatted) {
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(formatted, format)) {
                    return format.parse(formatted, ZonedDateTime::from);
                }
            }
        } else if (value instanceof Number number) {
            return java.time.Instant.ofEpochMilli(number.longValue()).atZone(java.time.ZoneId.systemDefault());
        }
        throw new Exception("Could not convert to an Instant value the value of class: " + value.getClass().getName());
    }

}
