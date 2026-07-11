package com.vidlus.jarch.mage;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class WizSqlTimestamp {

    private WizSqlTimestamp() {
    }

    /**
     * Checks if the given value can be converted to a java.sql.Timestamp.
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, Timestamp.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, java.util.Date.class)
            || WizLang.isChildOf(clazz, Date.class)
            || WizLang.isChildOf(clazz, Time.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    /**
     * Converts various object types into a java.sql.Timestamp.
     */
    public static Timestamp get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return Timestamp.valueOf(LocalDate.class.cast(value).atStartOfDay());
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.class.cast(value)));
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return Timestamp.valueOf(LocalDateTime.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return Timestamp.from(ZonedDateTime.class.cast(value).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return Timestamp.from(OffsetDateTime.class.cast(value).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), OffsetTime.class.cast(value).toLocalTime()));
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Timestamp.from(Instant.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return new Timestamp(java.util.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return new Timestamp(Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), Time.class.cast(value).toLocalTime()));
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    LocalDateTime parsed = format.parse(string, LocalDateTime::from);
                    return Timestamp.valueOf(parsed);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return new Timestamp(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to a java.sql.Timestamp value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current timestamp.
     */
    public static Timestamp now() {
        return Timestamp.from(Instant.now());
    }

    /**
     * Adds days securely.
     */
    public static Timestamp plusDays(Timestamp timestamp, long days) {
        if (timestamp == null) return null;
        return Timestamp.valueOf(timestamp.toLocalDateTime().plusDays(days));
    }

    /**
     * Subtracts days securely.
     */
    public static Timestamp minusDays(Timestamp timestamp, long days) {
        if (timestamp == null) return null;
        return Timestamp.valueOf(timestamp.toLocalDateTime().minusDays(days));
    }

    /**
     * Adds hours securely.
     */
    public static Timestamp plusHours(Timestamp timestamp, long hours) {
        if (timestamp == null) return null;
        return Timestamp.valueOf(timestamp.toLocalDateTime().plusHours(hours));
    }

    /**
     * Subtracts hours securely.
     */
    public static Timestamp minusHours(Timestamp timestamp, long hours) {
        if (timestamp == null) return null;
        return Timestamp.valueOf(timestamp.toLocalDateTime().minusHours(hours));
    }

    /**
     * Adds minutes securely.
     */
    public static Timestamp plusMinutes(Timestamp timestamp, long minutes) {
        if (timestamp == null) return null;
        return Timestamp.valueOf(timestamp.toLocalDateTime().plusMinutes(minutes));
    }

    /**
     * Subtracts minutes securely.
     */
    public static Timestamp minusMinutes(Timestamp timestamp, long minutes) {
        if (timestamp == null) return null;
        return Timestamp.valueOf(timestamp.toLocalDateTime().minusMinutes(minutes));
    }

    /**
     * Adds seconds securely.
     */
    public static Timestamp plusSeconds(Timestamp timestamp, long seconds) {
        if (timestamp == null) return null;
        return Timestamp.valueOf(timestamp.toLocalDateTime().plusSeconds(seconds));
    }

    /**
     * Subtracts seconds securely.
     */
    public static Timestamp minusSeconds(Timestamp timestamp, long seconds) {
        if (timestamp == null) return null;
        return Timestamp.valueOf(timestamp.toLocalDateTime().minusSeconds(seconds));
    }

    /**
     * Truncates the time component to exactly the start of the current hour.
     */
    public static Timestamp truncateToHours(Timestamp timestamp) {
        if (timestamp == null) return null;
        return Timestamp.valueOf(timestamp.toLocalDateTime().truncatedTo(ChronoUnit.HOURS));
    }

    /**
     * Truncates the time component to exactly the start of the current minute.
     */
    public static Timestamp truncateToMinutes(Timestamp timestamp) {
        if (timestamp == null) return null;
        return Timestamp.valueOf(timestamp.toLocalDateTime().truncatedTo(ChronoUnit.MINUTES));
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Checks if t1 is before t2 securely.
     */
    public static boolean isBefore(Timestamp t1, Timestamp t2) {
        if (t1 == null || t2 == null) return false;
        return t1.before(t2);
    }

    /**
     * Checks if t1 is after t2 securely.
     */
    public static boolean isAfter(Timestamp t1, Timestamp t2) {
        if (t1 == null || t2 == null) return false;
        return t1.after(t2);
    }

    /**
     * Checks if t1 is equal to t2 securely.
     */
    public static boolean isEqual(Timestamp t1, Timestamp t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.equals(t2);
    }

    /**
     * Returns the exact duration between two timestamps.
     */
    public static Duration between(Timestamp startInclusive, Timestamp endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive.toLocalDateTime(), endExclusive.toLocalDateTime());
    }

    /**
     * Calculates the exact number of days between two timestamps.
     */
    public static Long daysBetween(Timestamp startInclusive, Timestamp endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return ChronoUnit.DAYS.between(startInclusive.toLocalDateTime(), endExclusive.toLocalDateTime());
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Default formatter (machine format yyyy-MM-dd HH:mm:ss).
     */
    public static String format(Timestamp value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value.toLocalDateTime());
    }

    /**
     * Formats using the machine pattern (yyyy-MM-dd HH:mm:ss).
     */
    public static String formatDateTimeMach(Timestamp value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value.toLocalDateTime());
    }

    /**
     * Formats using the user pattern (dd/MM/yyyy HH:mm:ss).
     */
    public static String formatDateTimeUser(Timestamp value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeUser.format(value.toLocalDateTime());
    }

    /**
     * Formats using the file pattern (yyyy-MM-dd-HH-mm-ss).
     */
    public static String formatDateTimeFile(Timestamp value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeFile.format(value.toLocalDateTime());
    }

}
