package br.com.pointel.jarch.mage;

import java.time.OffsetTime;

public class WizOffsetTime {

    private WizOffsetTime() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), OffsetTime.class)
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

    public static OffsetTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return OffsetTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)) {
            return java.time.LocalDate.class.cast(value).atStartOfDay(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)) {
            java.time.ZoneOffset offset = java.time.ZoneId.systemDefault().getRules().getOffset(java.time.Instant.now());
            return OffsetTime.of(java.time.LocalTime.class.cast(value), offset);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)) {
            java.time.ZoneOffset offset = java.time.ZoneId.systemDefault().getRules().getOffset(java.time.Instant.now());
            return OffsetTime.of(java.time.LocalDateTime.class.cast(value).toLocalTime(), offset);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)) {
            return java.time.ZonedDateTime.class.cast(value).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)) {
            return java.time.OffsetDateTime.class.cast(value).toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.Instant.class)) {
            return java.time.Instant.class.cast(value).atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return java.util.Date.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Time.class)) {
            return java.sql.Time.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)) {
            return java.sql.Timestamp.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            String string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, OffsetTime::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return java.time.Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(java.time.ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        throw new Exception("Could not convert to an OffsetTime value the value of class: " + value.getClass().getName());
    }

}
