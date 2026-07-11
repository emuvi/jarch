package com.vidlus.jarch.mage;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
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

public class WizLocalDate {

    private WizLocalDate() {
    }

    /**
     * Checks if the given value can be converted to a LocalDate.
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
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
     * Converts various object types into a LocalDate.
     */
    public static LocalDate get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value).atDate(LocalDate.now()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return OffsetTime.class.cast(value).atDate(LocalDate.now()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, LocalDate::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Instant.ofEpochMilli(Number.class.cast(value).longValue()).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        throw new Exception("Could not convert to a LocalDate value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current date.
     */
    public static LocalDate now() {
        return LocalDate.now();
    }

    /**
     * Adds days securely, returning null if the input is null.
     */
    public static LocalDate plusDays(LocalDate date, long days) {
        if (date == null) return null;
        return date.plusDays(days);
    }

    /**
     * Subtracts days securely, returning null if the input is null.
     */
    public static LocalDate minusDays(LocalDate date, long days) {
        if (date == null) return null;
        return date.minusDays(days);
    }

    /**
     * Adds months securely.
     */
    public static LocalDate plusMonths(LocalDate date, long months) {
        if (date == null) return null;
        return date.plusMonths(months);
    }

    /**
     * Subtracts months securely.
     */
    public static LocalDate minusMonths(LocalDate date, long months) {
        if (date == null) return null;
        return date.minusMonths(months);
    }

    /**
     * Adds years securely.
     */
    public static LocalDate plusYears(LocalDate date, long years) {
        if (date == null) return null;
        return date.plusYears(years);
    }

    /**
     * Subtracts years securely.
     */
    public static LocalDate minusYears(LocalDate date, long years) {
        if (date == null) return null;
        return date.minusYears(years);
    }

    // =========================================================================
    // COMPARISONS & VALIDATIONS
    // =========================================================================

    /**
     * Checks if d1 is before d2 securely.
     */
    public static boolean isBefore(LocalDate d1, LocalDate d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isBefore(d2);
    }

    /**
     * Checks if d1 is after d2 securely.
     */
    public static boolean isAfter(LocalDate d1, LocalDate d2) {
        if (d1 == null || d2 == null) return false;
        return d1.isAfter(d2);
    }

    /**
     * Checks if d1 is equal to d2 securely.
     */
    public static boolean isEqual(LocalDate d1, LocalDate d2) {
        if (d1 == null && d2 == null) return true;
        if (d1 == null || d2 == null) return false;
        return d1.isEqual(d2);
    }

    /**
     * Checks if the given date represents "today".
     */
    public static boolean isToday(LocalDate date) {
        if (date == null) return false;
        return date.isEqual(now());
    }

    /**
     * Checks if the given date falls on a weekend (Saturday or Sunday).
     */
    public static boolean isWeekend(LocalDate date) {
        if (date == null) return false;
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    /**
     * Checks if the given date falls in a leap year.
     */
    public static boolean isLeapYear(LocalDate date) {
        if (date == null) return false;
        return date.isLeapYear();
    }

    /**
     * Calculates the exact number of days between two dates.
     */
    public static Long daysBetween(LocalDate startInclusive, LocalDate endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return ChronoUnit.DAYS.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Default formatter (machine format yyyy-MM-dd).
     */
    public static String format(LocalDate value) {
        if (value == null) return "";
        return WizInstant.formatDateMach.format(value);
    }

    /**
     * Formats using the machine pattern (yyyy-MM-dd).
     */
    public static String formatDateMach(LocalDate value) {
        if (value == null) return "";
        return WizInstant.formatDateMach.format(value);
    }

    /**
     * Formats using the user pattern (dd/MM/yyyy).
     */
    public static String formatDateUser(LocalDate value) {
        if (value == null) return "";
        return WizInstant.formatDateUser.format(value);
    }

    /**
     * Formats using the file pattern (yyyy-MM-dd).
     */
    public static String formatDateFile(LocalDate value) {
        if (value == null) return "";
        return WizInstant.formatDateFile.format(value);
    }

}
