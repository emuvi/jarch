package com.vidlus.jarch.mage;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Utility class providing null-safe conversion, manipulation, comparison, 
 * and formatting methods for java.time.OffsetTime objects.
 */
public class WizOffsetTime {

    private WizOffsetTime() {
    }

    /**
     * Checks if the given object can be successfully converted to an OffsetTime.
     * Supports various date/time types, numbers (epoch millis), and string formats.
     *
     * @param value the object to check
     * @return true if the object can be converted to an OffsetTime, false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, Date.class)
            || WizLang.isChildOf(clazz, java.sql.Date.class)
            || WizLang.isChildOf(clazz, Time.class)
            || WizLang.isChildOf(clazz, Timestamp.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    /**
     * Converts a wide variety of object types into an OffsetTime.
     * Extracts time information and applies the system default offset if necessary.
     * Supports standard Java date/time objects, SQL date/time, strings, and numeric epoch milliseconds.
     *
     * @param value the object to convert
     * @return the corresponding OffsetTime, or null if the input is null or an empty string
     * @throws Exception if the conversion fails or the type is unsupported
     */
    public static OffsetTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return OffsetTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
            return OffsetTime.of(LocalTime.class.cast(value), offset);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
            return OffsetTime.of(LocalDateTime.class.cast(value).toLocalTime(), offset);
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value).toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value).atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime()).atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
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
            return Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        throw new Exception("Could not convert to an OffsetTime value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current time with the system's default offset.
     *
     * @return the current OffsetTime
     */
    public static OffsetTime now() {
        return OffsetTime.now();
    }

    /**
     * Adds the specified number of hours to an OffsetTime in a null-safe manner.
     *
     * @param time  the original time
     * @param hours the number of hours to add
     * @return a new OffsetTime with the hours added, or null if the input time was null
     */
    public static OffsetTime plusHours(OffsetTime time, long hours) {
        if (time == null) return null;
        return time.plusHours(hours);
    }

    /**
     * Subtracts the specified number of hours from an OffsetTime in a null-safe manner.
     *
     * @param time  the original time
     * @param hours the number of hours to subtract
     * @return a new OffsetTime with the hours subtracted, or null if the input time was null
     */
    public static OffsetTime minusHours(OffsetTime time, long hours) {
        if (time == null) return null;
        return time.minusHours(hours);
    }

    /**
     * Adds the specified number of minutes to an OffsetTime in a null-safe manner.
     *
     * @param time    the original time
     * @param minutes the number of minutes to add
     * @return a new OffsetTime with the minutes added, or null if the input time was null
     */
    public static OffsetTime plusMinutes(OffsetTime time, long minutes) {
        if (time == null) return null;
        return time.plusMinutes(minutes);
    }

    /**
     * Subtracts the specified number of minutes from an OffsetTime in a null-safe manner.
     *
     * @param time    the original time
     * @param minutes the number of minutes to subtract
     * @return a new OffsetTime with the minutes subtracted, or null if the input time was null
     */
    public static OffsetTime minusMinutes(OffsetTime time, long minutes) {
        if (time == null) return null;
        return time.minusMinutes(minutes);
    }

    /**
     * Adds the specified number of seconds to an OffsetTime in a null-safe manner.
     *
     * @param time    the original time
     * @param seconds the number of seconds to add
     * @return a new OffsetTime with the seconds added, or null if the input time was null
     */
    public static OffsetTime plusSeconds(OffsetTime time, long seconds) {
        if (time == null) return null;
        return time.plusSeconds(seconds);
    }

    /**
     * Subtracts the specified number of seconds from an OffsetTime in a null-safe manner.
     *
     * @param time    the original time
     * @param seconds the number of seconds to subtract
     * @return a new OffsetTime with the seconds subtracted, or null if the input time was null
     */
    public static OffsetTime minusSeconds(OffsetTime time, long seconds) {
        if (time == null) return null;
        return time.minusSeconds(seconds);
    }

    /**
     * Truncates the time component to exactly the start of the current hour.
     * For example, 14:35:12 becomes 14:00:00.
     *
     * @param time the time to truncate
     * @return the truncated OffsetTime, or null if the input time was null
     */
    public static OffsetTime truncateToHours(OffsetTime time) {
        if (time == null) return null;
        return time.truncatedTo(ChronoUnit.HOURS);
    }

    /**
     * Truncates the time component to exactly the start of the current minute.
     * For example, 14:35:12 becomes 14:35:00.
     *
     * @param time the time to truncate
     * @return the truncated OffsetTime, or null if the input time was null
     */
    public static OffsetTime truncateToMinutes(OffsetTime time) {
        if (time == null) return null;
        return time.truncatedTo(ChronoUnit.MINUTES);
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Checks if the first OffsetTime is strictly before the second in a null-safe manner.
     *
     * @param t1 the first time
     * @param t2 the second time
     * @return true if t1 is before t2, false if either is null or the condition is not met
     */
    public static boolean isBefore(OffsetTime t1, OffsetTime t2) {
        if (t1 == null || t2 == null) return false;
        return t1.isBefore(t2);
    }

    /**
     * Checks if the first OffsetTime is strictly after the second in a null-safe manner.
     *
     * @param t1 the first time
     * @param t2 the second time
     * @return true if t1 is after t2, false if either is null or the condition is not met
     */
    public static boolean isAfter(OffsetTime t1, OffsetTime t2) {
        if (t1 == null || t2 == null) return false;
        return t1.isAfter(t2);
    }

    /**
     * Checks if two OffsetTime objects are exactly equal in a null-safe manner.
     *
     * @param t1 the first time
     * @param t2 the second time
     * @return true if both are null or both are equal, false otherwise
     */
    public static boolean isEqual(OffsetTime t1, OffsetTime t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.isEqual(t2);
    }

    /**
     * Calculates the exact duration between two OffsetTime objects.
     *
     * @param startInclusive the start time
     * @param endExclusive   the end time
     * @return a Duration representing the difference, or null if either time is null
     */
    public static Duration between(OffsetTime startInclusive, OffsetTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Formats the OffsetTime using the default machine format (HH:mm:ss).
     *
     * @param value the time to format
     * @return the formatted string, or an empty string if the input is null
     */
    public static String format(OffsetTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value);
    }

    /**
     * Formats the OffsetTime using the strict machine pattern (HH:mm:ss).
     *
     * @param value the time to format
     * @return the formatted string, or an empty string if the input is null
     */
    public static String formatTimeMach(OffsetTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value);
    }

    /**
     * Formats the OffsetTime using the user-friendly pattern (HH:mm:ss).
     *
     * @param value the time to format
     * @return the formatted string, or an empty string if the input is null
     */
    public static String formatTimeUser(OffsetTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeUser.format(value);
    }

    /**
     * Formats the OffsetTime using a file-safe pattern (HH-mm-ss).
     *
     * @param value the time to format
     * @return the formatted string, or an empty string if the input is null
     */
    public static String formatTimeFile(OffsetTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeFile.format(value);
    }

}
