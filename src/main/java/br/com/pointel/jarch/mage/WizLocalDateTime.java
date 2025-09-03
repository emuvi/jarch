package br.com.pointel.jarch.mage;

import java.time.LocalDateTime;

public class WizLocalDateTime {

    private WizLocalDateTime() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), LocalDateTime.class)
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

    public static LocalDateTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)) {
            return java.time.LocalDate.class.cast(value).atStartOfDay();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)) {
            return java.time.LocalTime.class.cast(value).atDate(java.time.LocalDate.now());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)) {
            return java.time.ZonedDateTime.class.cast(value).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)) {
            return java.time.OffsetDateTime.class.cast(value).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)) {
            var offsetTime = java.time.OffsetTime.class.cast(value);
            return offsetTime.atDate(java.time.LocalDate.now()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.Instant.class)) {
            return java.time.Instant.class.cast(value).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return java.util.Date.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Time.class)) {
            return java.sql.Time.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)) {
            return java.sql.Timestamp.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, LocalDateTime::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return java.time.Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        }
        throw new Exception("Could not convert to a LocalDateTime value the value of class: " + value.getClass().getName());
    }
}
