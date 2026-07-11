package com.vidlus.jarch.mage;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * A utility class for safely managing, parsing, formatting, and manipulating 
 * {@link java.time.ZonedDateTime} objects. Includes time-shift math and comparisons.
 */
public class WizZonedDateTime {

    private WizZonedDateTime() {
    }

    /**
     * Checks if the given object is a ZonedDateTime, another time object, 
     * or a String/Number capable of representing a time.
     *
     * @param value the object to check
     * @return true if the object represents a time
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), ZonedDateTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)
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

    /**
     * Intelligently parses and converts any date-like object into a {@link ZonedDateTime}.
     * Ensures all inputs fallback to the system default time zone if unzoned.
     *
     * @param value the object to convert
     * @return a normalized ZonedDateTime, or null if the input is null
     * @throws Exception if the object cannot be converted
     */
    public static ZonedDateTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)) {
            return java.time.LocalDate.class.cast(value).atStartOfDay(java.time.ZoneId.systemDefault());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)) {
            return java.time.LocalTime.class.cast(value).atDate(java.time.LocalDate.now()).atZone(java.time.ZoneId.systemDefault());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)) {
            return java.time.LocalDateTime.class.cast(value).atZone(java.time.ZoneId.systemDefault());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)) {
            return java.time.OffsetDateTime.class.cast(value).toZonedDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)) {
            return java.time.OffsetTime.class.cast(value).atDate(java.time.LocalDate.now()).atZoneSameInstant(java.time.ZoneId.systemDefault());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.Instant.class)) {
            return java.time.Instant.class.cast(value).atZone(java.time.ZoneId.systemDefault());
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return java.time.Instant.ofEpochMilli(java.util.Date.class.cast(value).getTime()).atZone(java.time.ZoneId.systemDefault());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Time.class)) {
            return java.sql.Time.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)) {
            return java.sql.Timestamp.class.cast(value).toInstant().atZone(java.time.ZoneId.systemDefault());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, ZonedDateTime::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return java.time.Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(java.time.ZoneId.systemDefault());
        }
        throw new Exception("Could not convert to a ZonedDateTime value the value of class: " + value.getClass().getName());
    }

    /**
     * Formats a ZonedDateTime into a standard machine-readable string via WizInstant.
     *
     * @param value the time to format
     * @return the formatted string
     */
    public static String format(ZonedDateTime value) {
        if (value == null) return "";
        return WizInstant.formatInstantMach.format(value);
    }

    // =========================================================================
    // DATE MANIPULATION (MATH)
    // =========================================================================

    /**
     * @return the current time with the system's default time zone
     */
    public static ZonedDateTime now() {
        return ZonedDateTime.now();
    }

    /**
     * Converts the time to UTC safely.
     *
     * @param value the time
     * @return a ZonedDateTime strictly at UTC
     */
    public static ZonedDateTime toUTC(ZonedDateTime value) {
        if (value == null) return null;
        return value.withZoneSameInstant(ZoneId.of("UTC"));
    }

    /**
     * Adds (or subtracts) a specific number of years.
     *
     * @param value  the source time
     * @param amount the number of years to add (negative to subtract)
     * @return a new time shifted
     */
    public static ZonedDateTime addYears(ZonedDateTime value, long amount) {
        if (value == null) return null;
        return value.plusYears(amount);
    }

    /**
     * Adds (or subtracts) a specific number of months.
     *
     * @param value  the source time
     * @param amount the number of months to add (negative to subtract)
     * @return a new time shifted
     */
    public static ZonedDateTime addMonths(ZonedDateTime value, long amount) {
        if (value == null) return null;
        return value.plusMonths(amount);
    }

    /**
     * Adds (or subtracts) a specific number of days.
     *
     * @param value  the source time
     * @param amount the number of days to add (negative to subtract)
     * @return a new time shifted
     */
    public static ZonedDateTime addDays(ZonedDateTime value, long amount) {
        if (value == null) return null;
        return value.plusDays(amount);
    }

    /**
     * Adds (or subtracts) a specific number of hours.
     *
     * @param value  the source time
     * @param amount the number of hours to add (negative to subtract)
     * @return a new time shifted
     */
    public static ZonedDateTime addHours(ZonedDateTime value, long amount) {
        if (value == null) return null;
        return value.plusHours(amount);
    }

    /**
     * Adds (or subtracts) a specific number of minutes.
     *
     * @param value  the source time
     * @param amount the number of minutes to add (negative to subtract)
     * @return a new time shifted
     */
    public static ZonedDateTime addMinutes(ZonedDateTime value, long amount) {
        if (value == null) return null;
        return value.plusMinutes(amount);
    }

    /**
     * Adds (or subtracts) a specific number of seconds.
     *
     * @param value  the source time
     * @param amount the number of seconds to add (negative to subtract)
     * @return a new time shifted
     */
    public static ZonedDateTime addSeconds(ZonedDateTime value, long amount) {
        if (value == null) return null;
        return value.plusSeconds(amount);
    }

    // =========================================================================
    // COMPARISONS
    // =========================================================================

    /**
     * Null-safe comparison to check if one time is strictly before another.
     *
     * @param t1 the first time
     * @param t2 the second time
     * @return true if t1 occurs before t2
     */
    public static boolean isBefore(ZonedDateTime t1, ZonedDateTime t2) {
        if (t1 == null || t2 == null) return false;
        return t1.isBefore(t2);
    }

    /**
     * Null-safe comparison to check if one time is strictly after another.
     *
     * @param t1 the first time
     * @param t2 the second time
     * @return true if t1 occurs after t2
     */
    public static boolean isAfter(ZonedDateTime t1, ZonedDateTime t2) {
        if (t1 == null || t2 == null) return false;
        return t1.isAfter(t2);
    }

    /**
     * Null-safe equality check. Considers timeline points equal.
     *
     * @param t1 the first time
     * @param t2 the second time
     * @return true if t1 and t2 represent the exact same instant
     */
    public static boolean isEqual(ZonedDateTime t1, ZonedDateTime t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.isEqual(t2);
    }

    /**
     * Calculates the duration between two times in milliseconds.
     *
     * @param t1 the start time
     * @param t2 the end time
     * @return the number of milliseconds between t1 and t2
     */
    public static long betweenMillis(ZonedDateTime t1, ZonedDateTime t2) {
        if (t1 == null || t2 == null) return 0;
        return ChronoUnit.MILLIS.between(t1, t2);
    }

    /**
     * Calculates the duration between two times in days.
     *
     * @param t1 the start time
     * @param t2 the end time
     * @return the number of days between t1 and t2
     */
    public static long betweenDays(ZonedDateTime t1, ZonedDateTime t2) {
        if (t1 == null || t2 == null) return 0;
        return ChronoUnit.DAYS.between(t1, t2);
    }
}
