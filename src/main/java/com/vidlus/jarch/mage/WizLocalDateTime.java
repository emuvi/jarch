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

public class WizLocalDateTime {

    private WizLocalDateTime() {
    }

    /**
     * Checks if the given value can be converted to a LocalDateTime.
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
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
     * Converts various object types into a LocalDateTime.
     */
    public static LocalDateTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value).atStartOfDay();
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value).atDate(LocalDate.now());
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            var offsetTime = OffsetTime.class.cast(value);
            return offsetTime.atDate(LocalDate.now()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
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
            return Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        throw new Exception("Could not convert to a LocalDateTime value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current local date and time.
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * Adds days securely.
     */
    public static LocalDateTime plusDays(LocalDateTime dateTime, long days) {
        if (dateTime == null) return null;
        return dateTime.plusDays(days);
    }

    /**
     * Subtracts days securely.
     */
    public static LocalDateTime minusDays(LocalDateTime dateTime, long days) {
        if (dateTime == null) return null;
        return dateTime.minusDays(days);
    }

    /**
     * Adds hours securely.
     */
    public static LocalDateTime plusHours(LocalDateTime dateTime, long hours) {
        if (dateTime == null) return null;
        return dateTime.plusHours(hours);
    }

    /**
     * Subtracts hours securely.
     */
    public static LocalDateTime minusHours(LocalDateTime dateTime, long hours) {
        if (dateTime == null) return null;
        return dateTime.minusHours(hours);
    }

    /**
     * Adds minutes securely.
     */
    public static LocalDateTime plusMinutes(LocalDateTime dateTime, long minutes) {
        if (dateTime == null) return null;
        return dateTime.plusMinutes(minutes);
    }

    /**
     * Subtracts minutes securely.
     */
    public static LocalDateTime minusMinutes(LocalDateTime dateTime, long minutes) {
        if (dateTime == null) return null;
        return dateTime.minusMinutes(minutes);
    }

    /**
     * Adds seconds securely.
     */
    public static LocalDateTime plusSeconds(LocalDateTime dateTime, long seconds) {
        if (dateTime == null) return null;
        return dateTime.plusSeconds(seconds);
    }

    /**
     * Subtracts seconds securely.
     */
    public static LocalDateTime minusSeconds(LocalDateTime dateTime, long seconds) {
        if (dateTime == null) return null;
        return dateTime.minusSeconds(seconds);
    }

    /**
     * Truncates the time component to exactly the start of the current hour.
     */
    public static LocalDateTime truncateToHours(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.truncatedTo(ChronoUnit.HOURS);
    }

    /**
     * Truncates the time component to exactly the start of the current minute.
     */
    public static LocalDateTime truncateToMinutes(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.truncatedTo(ChronoUnit.MINUTES);
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Checks if d1 is before d2 securely.
     */
    public static boolean isBefore(LocalDateTime d1, LocalDateTime d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isBefore(d2);
    }

    /**
     * Checks if d1 is after d2 securely.
     */
    public static boolean isAfter(LocalDateTime d1, LocalDateTime d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isAfter(d2);
    }

    /**
     * Checks if d1 is equal to d2 securely.
     */
    public static boolean isEqual(LocalDateTime d1, LocalDateTime d2) {
        if (d1 == null && d2 == null) return true;
        if (d1 == null || d2 == null) return false;
        return d1.isEqual(d2);
    }

    /**
     * Returns the exact duration between two date-times.
     */
    public static Duration between(LocalDateTime startInclusive, LocalDateTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive, endExclusive);
    }

    /**
     * Calculates the exact number of days between two date-times.
     */
    public static Long daysBetween(LocalDateTime startInclusive, LocalDateTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return ChronoUnit.DAYS.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Default formatter (machine format yyyy-MM-dd HH:mm:ss).
     */
    public static String format(LocalDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value);
    }

    /**
     * Formats using the machine pattern (yyyy-MM-dd HH:mm:ss).
     */
    public static String formatDateTimeMach(LocalDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value);
    }

    /**
     * Formats using the user pattern (dd/MM/yyyy HH:mm:ss).
     */
    public static String formatDateTimeUser(LocalDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeUser.format(value);
    }

    /**
     * Formats using the file pattern (yyyy-MM-dd-HH-mm-ss).
     */
    public static String formatDateTimeFile(LocalDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeFile.format(value);
    }

}
