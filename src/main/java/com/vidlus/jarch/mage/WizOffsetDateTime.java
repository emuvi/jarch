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

public class WizOffsetDateTime {

    private WizOffsetDateTime() {
    }

    /**
     * Checks if the given value can be converted to an OffsetDateTime.
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
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
     * Converts various object types into an OffsetDateTime.
     */
    public static OffsetDateTime get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value).atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            OffsetTime offsetTime = OffsetTime.class.cast(value);
            ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
            return OffsetDateTime.of(LocalDate.now(), offsetTime.toLocalTime(), offset);
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime()).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();
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
            return Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        throw new Exception("Could not convert to an OffsetDateTime value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current offset date and time.
     */
    public static OffsetDateTime now() {
        return OffsetDateTime.now();
    }

    /**
     * Adds days securely.
     */
    public static OffsetDateTime plusDays(OffsetDateTime dateTime, long days) {
        if (dateTime == null) return null;
        return dateTime.plusDays(days);
    }

    /**
     * Subtracts days securely.
     */
    public static OffsetDateTime minusDays(OffsetDateTime dateTime, long days) {
        if (dateTime == null) return null;
        return dateTime.minusDays(days);
    }

    /**
     * Adds hours securely.
     */
    public static OffsetDateTime plusHours(OffsetDateTime dateTime, long hours) {
        if (dateTime == null) return null;
        return dateTime.plusHours(hours);
    }

    /**
     * Subtracts hours securely.
     */
    public static OffsetDateTime minusHours(OffsetDateTime dateTime, long hours) {
        if (dateTime == null) return null;
        return dateTime.minusHours(hours);
    }

    /**
     * Adds minutes securely.
     */
    public static OffsetDateTime plusMinutes(OffsetDateTime dateTime, long minutes) {
        if (dateTime == null) return null;
        return dateTime.plusMinutes(minutes);
    }

    /**
     * Subtracts minutes securely.
     */
    public static OffsetDateTime minusMinutes(OffsetDateTime dateTime, long minutes) {
        if (dateTime == null) return null;
        return dateTime.minusMinutes(minutes);
    }

    /**
     * Adds seconds securely.
     */
    public static OffsetDateTime plusSeconds(OffsetDateTime dateTime, long seconds) {
        if (dateTime == null) return null;
        return dateTime.plusSeconds(seconds);
    }

    /**
     * Subtracts seconds securely.
     */
    public static OffsetDateTime minusSeconds(OffsetDateTime dateTime, long seconds) {
        if (dateTime == null) return null;
        return dateTime.minusSeconds(seconds);
    }

    /**
     * Truncates the time component to exactly the start of the current hour.
     */
    public static OffsetDateTime truncateToHours(OffsetDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.truncatedTo(ChronoUnit.HOURS);
    }

    /**
     * Truncates the time component to exactly the start of the current minute.
     */
    public static OffsetDateTime truncateToMinutes(OffsetDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.truncatedTo(ChronoUnit.MINUTES);
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Checks if d1 is before d2 securely.
     */
    public static boolean isBefore(OffsetDateTime d1, OffsetDateTime d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isBefore(d2);
    }

    /**
     * Checks if d1 is after d2 securely.
     */
    public static boolean isAfter(OffsetDateTime d1, OffsetDateTime d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isAfter(d2);
    }

    /**
     * Checks if d1 is equal to d2 securely.
     */
    public static boolean isEqual(OffsetDateTime d1, OffsetDateTime d2) {
        if (d1 == null && d2 == null) return true;
        if (d1 == null || d2 == null) return false;
        return d1.isEqual(d2);
    }

    /**
     * Returns the exact duration between two offset date-times.
     */
    public static Duration between(OffsetDateTime startInclusive, OffsetDateTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive, endExclusive);
    }

    /**
     * Calculates the exact number of days between two offset date-times.
     */
    public static Long daysBetween(OffsetDateTime startInclusive, OffsetDateTime endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return ChronoUnit.DAYS.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Default formatter (machine format yyyy-MM-dd HH:mm:ss).
     */
    public static String format(OffsetDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value);
    }

    /**
     * Formats using the machine pattern (yyyy-MM-dd HH:mm:ss).
     */
    public static String formatDateTimeMach(OffsetDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value);
    }

    /**
     * Formats using the user pattern (dd/MM/yyyy HH:mm:ss).
     */
    public static String formatDateTimeUser(OffsetDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeUser.format(value);
    }

    /**
     * Formats using the file pattern (yyyy-MM-dd-HH-mm-ss).
     */
    public static String formatDateTimeFile(OffsetDateTime value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeFile.format(value);
    }

}
