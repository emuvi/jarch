package br.com.pointel.jarch.mage;

import java.sql.Timestamp;

public class WizSqlTimestamp {

    private WizSqlTimestamp() {
    }

    public static boolean is(Object value) {
        if (value == null) {
            return false;
        }
        return WizLang.isChildOf(value.getClass(), Timestamp.class)
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

    public static Timestamp get(Object data) throws Exception {
        if (data == null) {
            return null;
        }
        if (WizLang.isChildOf(data.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(data);
        }
        if (data instanceof java.time.LocalDate localDate) {
            return Timestamp.valueOf(localDate.atStartOfDay());
        } else if (data instanceof java.time.LocalTime localTime) {
            return Timestamp.valueOf(java.time.LocalDateTime.of(java.time.LocalDate.now(), localTime));
        } else if (data instanceof java.time.LocalDateTime localDateTime) {
            return Timestamp.valueOf(localDateTime);
        } else if (data instanceof java.time.ZonedDateTime zonedDateTime) {
            return Timestamp.from(zonedDateTime.toInstant());
        } else if (data instanceof java.time.OffsetDateTime offsetDateTime) {
            return Timestamp.from(offsetDateTime.toInstant());
        } else if (data instanceof java.time.OffsetTime offsetTime) {
            return Timestamp.valueOf(java.time.LocalDateTime.of(java.time.LocalDate.now(), offsetTime.toLocalTime()));
        } else if (data instanceof java.time.Instant instant) {
            return Timestamp.from(instant);
        } else if (data instanceof java.util.Date date) {
            return new Timestamp(date.getTime());
        } else if (data instanceof java.sql.Date date) {
            return new Timestamp(date.getTime());
        } else if (data instanceof java.sql.Time time) {
            return Timestamp.valueOf(java.time.LocalDateTime.of(java.time.LocalDate.now(), time.toLocalTime()));
        } else if (data instanceof java.sql.Timestamp timestamp) {
            return timestamp;
        } else if (data instanceof String formatted) {
            for (var format : WizUtilDate.getFormats()) {
                if (WizUtilDate.is(formatted, format)) {
                    java.util.Date parsed = format.parse(formatted);
                    return new Timestamp(parsed.getTime());
                }
            }
        } else if (data instanceof Number number) {
            return new Timestamp(number.longValue());
        }
        throw new Exception("Could not convert to a Date value the value of class: " + data.getClass().getName());
    }

}
