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

public class WizSqlTime {

    private WizSqlTime() {
    }

    /**
     * Checks if the given value can be converted to a java.sql.Time.
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, Time.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, java.util.Date.class)
            || WizLang.isChildOf(clazz, Date.class)
            || WizLang.isChildOf(clazz, Timestamp.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    /**
     * Converts various object types into a java.sql.Time.
     */
    public static Time get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return Time.valueOf(LocalTime.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return Time.valueOf(LocalDateTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return Time.valueOf(ZonedDateTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return Time.valueOf(OffsetDateTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return Time.valueOf(OffsetTime.class.cast(value).toLocalTime());
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return new Time(Instant.class.cast(value).toEpochMilli());
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return new Time(java.util.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return new Time(Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return new Time(Timestamp.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    LocalTime parsed = format.parse(string, LocalTime::from);
                    return Time.valueOf(parsed);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return new Time(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to a java.sql.Time value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current time.
     */
    public static Time now() {
        return Time.valueOf(LocalTime.now());
    }

    /**
     * Adds hours securely.
     */
    public static Time plusHours(Time time, long hours) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().plusHours(hours));
    }

    /**
     * Subtracts hours securely.
     */
    public static Time minusHours(Time time, long hours) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().minusHours(hours));
    }

    /**
     * Adds minutes securely.
     */
    public static Time plusMinutes(Time time, long minutes) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().plusMinutes(minutes));
    }

    /**
     * Subtracts minutes securely.
     */
    public static Time minusMinutes(Time time, long minutes) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().minusMinutes(minutes));
    }

    /**
     * Adds seconds securely.
     */
    public static Time plusSeconds(Time time, long seconds) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().plusSeconds(seconds));
    }

    /**
     * Subtracts seconds securely.
     */
    public static Time minusSeconds(Time time, long seconds) {
        if (time == null) return null;
        return Time.valueOf(time.toLocalTime().minusSeconds(seconds));
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Checks if t1 is before t2 securely.
     */
    public static boolean isBefore(Time t1, Time t2) {
        if (t1 == null || t2 == null) return false;
        return t1.before(t2);
    }

    /**
     * Checks if t1 is after t2 securely.
     */
    public static boolean isAfter(Time t1, Time t2) {
        if (t1 == null || t2 == null) return false;
        return t1.after(t2);
    }

    /**
     * Checks if t1 is equal to t2 securely.
     */
    public static boolean isEqual(Time t1, Time t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.toLocalTime().equals(t2.toLocalTime());
    }

    /**
     * Returns the exact duration between two times.
     */
    public static Duration between(Time startInclusive, Time endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive.toLocalTime(), endExclusive.toLocalTime());
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Default formatter (machine format HH:mm:ss).
     */
    public static String format(Time value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value.toLocalTime());
    }

    /**
     * Formats using the machine pattern (HH:mm:ss).
     */
    public static String formatTimeMach(Time value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value.toLocalTime());
    }

    /**
     * Formats using the user pattern (HH:mm:ss).
     */
    public static String formatTimeUser(Time value) {
        if (value == null) return "";
        return WizInstant.formatTimeUser.format(value.toLocalTime());
    }

    /**
     * Formats using the file pattern (HH-mm-ss).
     */
    public static String formatTimeFile(Time value) {
        if (value == null) return "";
        return WizInstant.formatTimeFile.format(value.toLocalTime());
    }

}
