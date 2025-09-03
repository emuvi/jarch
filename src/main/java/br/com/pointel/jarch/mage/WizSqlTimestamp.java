package br.com.pointel.jarch.mage;

import java.sql.Timestamp;

public class WizSqlTimestamp {

    private WizSqlTimestamp() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Timestamp.class)
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

    public static Timestamp get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)) {
            return Timestamp.valueOf(java.time.LocalDate.class.cast(value).atStartOfDay());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)) {
            return Timestamp.valueOf(java.time.LocalDateTime.of(java.time.LocalDate.now(), java.time.LocalTime.class.cast(value)));
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)) {
            return Timestamp.valueOf(java.time.LocalDateTime.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)) {
            return Timestamp.from(java.time.ZonedDateTime.class.cast(value).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)) {
            return Timestamp.from(java.time.OffsetDateTime.class.cast(value).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)) {
            return Timestamp.valueOf(java.time.LocalDateTime.of(java.time.LocalDate.now(), java.time.OffsetTime.class.cast(value).toLocalTime()));
        }
        if (WizLang.isChildOf(value.getClass(), java.time.Instant.class)) {
            return Timestamp.from(java.time.Instant.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return new Timestamp(java.util.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return new Timestamp(java.sql.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Time.class)) {
            return Timestamp.valueOf(java.time.LocalDateTime.of(java.time.LocalDate.now(), java.sql.Time.class.cast(value).toLocalTime()));
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)) {
            return java.sql.Timestamp.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizUtilDate.getFormats()) {
                if (WizUtilDate.is(string, format)) {
                    var parsed = format.parse(string);
                    return new Timestamp(parsed.getTime());
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return new Timestamp(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to a Date value the value of class: " + value.getClass().getName());
    }

}
