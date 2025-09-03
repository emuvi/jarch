package br.com.pointel.jarch.mage;

import java.sql.Date;

public class WizSqlDate {

    private WizSqlDate() {
    }

    public static boolean is(Object value) {
        if (value == null) {
            return false;
        }
        return WizLang.isChildOf(value.getClass(), Date.class)
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

    public static Date get(Object data) throws Exception {
        if (data == null) {
            return null;
        }
        if (WizLang.isChildOf(data.getClass(), Date.class)) {
            return Date.class.cast(data);
        }
        if (data instanceof java.time.LocalDate localDate) {
            return Date.valueOf(localDate);
        } else if (data instanceof java.time.LocalTime) {
            return Date.valueOf(java.time.LocalDate.now());
        } else if (data instanceof java.time.LocalDateTime localDateTime) {
            return Date.valueOf(localDateTime.toLocalDate());
        } else if (data instanceof java.time.ZonedDateTime zonedDateTime) {
            return Date.valueOf(zonedDateTime.toLocalDate());
        } else if (data instanceof java.time.OffsetDateTime offsetDateTime) {
            return Date.valueOf(offsetDateTime.toLocalDate());
        } else if (data instanceof java.time.OffsetTime) {
            return Date.valueOf(java.time.LocalDate.now());
        } else if (data instanceof java.time.Instant instant) {
            return new Date(instant.toEpochMilli());
        } else if (data instanceof java.util.Date date) {
            return new Date(date.getTime());
        } else if (data instanceof Date date) {
            return date;
        } else if (data instanceof java.sql.Time time) {
            return new Date(time.getTime());
        } else if (data instanceof java.sql.Timestamp timestamp) {
            return new Date(timestamp.getTime());
        } else if (data instanceof String formatted) {
            for (var format : WizUtilDate.getFormats()) {
                if (WizUtilDate.is(formatted, format)) {
                    java.util.Date parsed = format.parse(formatted);
                    return new Date(parsed.getTime());
                }
            }
        } else if (data instanceof Number number) {
            return new Date(number.longValue());
        }
        throw new Exception("Could not convert to a Date value the value of class: " + data.getClass().getName());
    }

}
