package br.com.pointel.jarch.mage;

import java.sql.Time;

public class WizSqlTime {

    private WizSqlTime() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Time.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.Instant.class)
            || WizLang.isChildOf(value.getClass(), java.util.Date.class)
            || WizLang.isChildOf(value.getClass(), java.sql.Date.class)
            || WizLang.isChildOf(value.getClass(), java.sql.Time.class)
            || WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class);
    }

    public static Time get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)) {
            return Time.valueOf(java.time.LocalTime.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)) {
            return Time.valueOf(java.time.LocalDateTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)) {
            return Time.valueOf(java.time.ZonedDateTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)) {
            return Time.valueOf(java.time.OffsetDateTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)) {
            return Time.valueOf(java.time.OffsetTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.Instant.class)) {
            return new Time(java.time.Instant.class.cast(value).toEpochMilli());
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return new Time(java.util.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return new Time(java.sql.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)) {
            return new Time(java.sql.Timestamp.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizUtilDate.getFormats()) {
                if (WizUtilDate.is(string, format)) {
                    var parsed = format.parse(string);
                    return new Time(parsed.getTime());
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return new Time(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to a Time value the value of class: " + value.getClass().getName());
    }

}
