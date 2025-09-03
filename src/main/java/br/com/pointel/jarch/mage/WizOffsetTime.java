package br.com.pointel.jarch.mage;

import java.time.OffsetTime;

public class WizOffsetTime {

    private WizOffsetTime() {
    }

    public static boolean is(Object value) {
        if (value == null) {
            return false;
        }
        return WizLang.isChildOf(value.getClass(), OffsetTime.class)
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

    public static OffsetTime get(Object data) throws Exception {
        if (data == null) {
            return null;
        }
        if (WizLang.isChildOf(data.getClass(), OffsetTime.class)) {
            return OffsetTime.class.cast(data);
        }
        if (data instanceof java.time.LocalDate localDate) {
            return localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        } else if (data instanceof java.time.LocalTime localTime) {
            java.time.LocalDate today = java.time.LocalDate.now();
            java.time.ZoneOffset offset = java.time.ZoneId.systemDefault().getRules().getOffset(java.time.Instant.now());
            return OffsetTime.of(localTime, offset);
        } else if (data instanceof java.time.LocalDateTime localDateTime) {
            java.time.ZoneOffset offset = java.time.ZoneId.systemDefault().getRules().getOffset(java.time.Instant.now());
            return OffsetTime.of(localDateTime.toLocalTime(), offset);
        } else if (data instanceof java.time.ZonedDateTime zonedDateTime) {
            return zonedDateTime.toOffsetDateTime().toOffsetTime();
        } else if (data instanceof java.time.OffsetDateTime offsetDateTime) {
            return offsetDateTime.toOffsetTime();
        } else if (data instanceof java.time.OffsetTime offsetTime) {
            return offsetTime;
        } else if (data instanceof java.time.Instant instant) {
            return instant.atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        } else if (data instanceof java.util.Date date) {
            return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        } else if (data instanceof java.sql.Date date) {
            return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        } else if (data instanceof java.sql.Time time) {
            return time.toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        } else if (data instanceof java.sql.Timestamp timestamp) {
            return timestamp.toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        } else if (data instanceof String formatted) {
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(formatted, format)) {
                    return format.parse(formatted, OffsetTime::from);
                }
            }
        } else if (data instanceof Number number) {
            return java.time.Instant.ofEpochMilli(number.longValue()).atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        throw new Exception("Could not convert to an Instant value the value of class: " + data.getClass().getName());
    }

}
