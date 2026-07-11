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
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class WizLocalTime {

    private WizLocalTime() {
    }

    /**
     * Checks if the given value can be converted to a LocalTime.
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, Date.class)
            || WizLang.isChildOf(clazz, java.sql.Date.class)
            || WizLang.isChildOf(clazz, Time.class)
            || WizLang.isChildOf(clazz, Timestamp.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    /**
     * Converts various object types into a LocalTime.
     */
    public static LocalTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value).atStartOfDay(ZoneId.systemDefault()).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return OffsetTime.class.cast(value).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value).atZone(ZoneId.systemDefault()).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toLocalDate().atStartOfDay().toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toLocalDateTime().toLocalTime();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, LocalTime::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(ZoneId.systemDefault()).toLocalTime();
        }
        throw new Exception("Could not convert to a LocalTime value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current local time.
     */
    public static LocalTime now() {
        return LocalTime.now();
    }

    /**
     * Adds hours securely.
     */
    public static LocalTime plusHours(LocalTime time, long hours) {
        if (time == null) return null;
        return time.plusHours(hours);
    }

    /**
     * Subtracts hours securely.
     */
    public static LocalTime minusHours(LocalTime time, long hours) {
        if (time == null) return null;
        return time.minusHours(hours);
    }

    /**
     * Adds minutes securely.
     */
    public static LocalTime plusMinutes(LocalTime time, long minutes) {
        if (time == null) return null;
        return time.plusMinutes(minutes);
    }

    /**
     * Subtracts minutes securely.
     */
    public static LocalTime minusMinutes(LocalTime time, long minutes) {
        if (time == null) return null;
        return time.minusMinutes(minutes);
    }

    /**
     * Adds seconds securely.
     */
    public static LocalTime plusSeconds(LocalTime time, long seconds) {
        if (time == null) return null;
        return time.plusSeconds(seconds);
    }

    /**
     * Subtracts seconds securely.
     */
    public static LocalTime minusSeconds(LocalTime time, long seconds) {
        if (time == null) return null;
        return time.minusSeconds(seconds);
    }

    /**
     * Truncates the time component to exactly the start of the current hour.
     */
    public static LocalTime truncateToHours(LocalTime time) {
        if (time == null) return null;
        return time.truncatedTo(ChronoUnit.HOURS);
    }

    /**
     * Truncates the time component to exactly the start of the current minute.
     */
    public static LocalTime truncateToMinutes(LocalTime time) {
        if (time == null) return null;
        return time.truncatedTo(ChronoUnit.MINUTES);
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Checks if t1 is before t2 securely.
     */
    public static boolean isBefore(LocalTime t1, LocalTime t2) {
        if (t1 == null || t2 == null) return false;
        return t1.isBefore(t2);
    }

    /**
     * Checks if t1 is after t2 securely.
     */
    public static boolean isAfter(LocalTime t1, LocalTime t2) {
        if (t1 == null || t2 == null) return false;
        return t1.isAfter(t2);
    }

    /**
     * Checks if t1 is equal to t2 securely.
     */
    public static boolean isEqual(LocalTime t1, LocalTime t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.equals(t2);
    }

    /**
     * Returns the exact duration between two times.
     */
    public static Duration between(LocalTime startInclusive, LocalTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Default formatter (machine format HH:mm:ss).
     */
    public static String format(LocalTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value);
    }

    /**
     * Formats using the machine pattern (HH:mm:ss).
     */
    public static String formatTimeMach(LocalTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value);
    }

    /**
     * Formats using the user pattern (HH:mm:ss).
     */
    public static String formatTimeUser(LocalTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeUser.format(value);
    }

    /**
     * Formats using the file pattern (HH-mm-ss).
     */
    public static String formatTimeFile(LocalTime value) {
        if (value == null) return "";
        return WizInstant.formatTimeFile.format(value);
    }

}
