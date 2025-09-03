package br.com.pointel.jarch.mage;

import java.sql.Time;

public class WizSqlTime {

    private WizSqlTime() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Time.class)
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

    public static Time get(Object value) throws Exception {
        if (value == null) {
            return null;
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value);
        }
        if (value instanceof java.time.LocalTime localTime) {
            return Time.valueOf(localTime);
        } else if (value instanceof java.time.LocalDateTime localDateTime) {
            return Time.valueOf(localDateTime.toLocalTime());
        } else if (value instanceof java.time.ZonedDateTime zonedDateTime) {
            return Time.valueOf(zonedDateTime.toLocalTime());
        } else if (value instanceof java.time.OffsetDateTime offsetDateTime) {
            return Time.valueOf(offsetDateTime.toLocalTime());
        } else if (value instanceof java.time.OffsetTime offsetTime) {
            return Time.valueOf(offsetTime.toLocalTime());
        } else if (value instanceof java.time.Instant instant) {
            return new Time(instant.toEpochMilli());
        } else if (value instanceof java.util.Date date) {
            return new Time(date.getTime());
        } else if (value instanceof java.sql.Date date) {
            return new Time(date.getTime());
        } else if (value instanceof Time time) {
            return time;
        } else if (value instanceof java.sql.Timestamp timestamp) {
            return new Time(timestamp.getTime());
        } else if (value instanceof String formatted) {
            for (var format : WizUtilDate.getFormats()) {
                if (WizUtilDate.is(formatted, format)) {
                    java.util.Date parsed = format.parse(formatted);
                    return new Time(parsed.getTime());
                }
            }
        } else if (value instanceof Number number) {
            return new Time(number.longValue());
        }
        throw new Exception("Could not convert to a Date value the value of class: " + value.getClass().getName());
    }

}
