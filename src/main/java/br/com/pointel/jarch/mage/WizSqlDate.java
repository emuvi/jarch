package br.com.pointel.jarch.mage;

import java.sql.Date;

public class WizSqlDate {

    private WizSqlDate() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Date.class)
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

    public static Date get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Date.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)) {
            return Date.valueOf(java.time.LocalDate.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)) {
            return Date.valueOf(java.time.LocalDate.now());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)) {
            return Date.valueOf(java.time.LocalDateTime.class.cast(value).toLocalDate());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)) {
            return Date.valueOf(java.time.ZonedDateTime.class.cast(value).toLocalDate());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)) {
            return Date.valueOf(java.time.OffsetDateTime.class.cast(value).toLocalDate());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)) {
            return Date.valueOf(java.time.LocalDate.now());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.Instant.class)) {
            return new Date(java.time.Instant.class.cast(value).toEpochMilli());
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return new Date(java.util.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Time.class)) {
            return new Date(java.sql.Time.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)) {
            return new Date(java.sql.Timestamp.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizUtilDate.getFormats()) {
                if (WizUtilDate.is(string, format)) {
                    var parsed = format.parse(string);
                    return new Date(parsed.getTime());
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return new Date(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to a Date value the value of class: " + value.getClass().getName());
    }

}
