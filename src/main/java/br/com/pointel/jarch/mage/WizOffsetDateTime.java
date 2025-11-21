package br.com.pointel.jarch.mage;

import java.time.OffsetDateTime;

public class WizOffsetDateTime {

    private WizOffsetDateTime() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), OffsetDateTime.class)
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

    public static OffsetDateTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)) {
            return java.time.LocalDate.class.cast(value).atStartOfDay(java.time.ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)) {
            return java.time.LocalTime.class.cast(value).atDate(java.time.LocalDate.now()).atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)) {
            return java.time.LocalDateTime.class.cast(value).atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)) {
            return java.time.ZonedDateTime.class.cast(value).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)) {
            java.time.OffsetTime offsetTime = java.time.OffsetTime.class.cast(value);
            java.time.ZoneOffset offset = java.time.ZoneId.systemDefault().getRules().getOffset(java.time.Instant.now());
            return OffsetDateTime.of(java.time.LocalDate.now(), offsetTime.toLocalTime(), offset);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.Instant.class)) {
            return java.time.Instant.class.cast(value).atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return java.time.Instant.ofEpochMilli(java.util.Date.class.cast(value).getTime()).atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Time.class)) {
            return java.sql.Time.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)) {
            return java.sql.Timestamp.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, OffsetDateTime::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return java.time.Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime();
        }
        throw new Exception("Could not convert to an OffsetDateTime value the value of class: " + value.getClass().getName());
    }

}
