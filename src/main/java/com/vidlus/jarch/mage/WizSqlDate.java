package com.vidlus.jarch.mage;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class WizSqlDate {

    private WizSqlDate() {
    }

    /**
     * Checks if the given value can be converted to a java.sql.Date.
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, Date.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, java.util.Date.class)
            || WizLang.isChildOf(clazz, Time.class)
            || WizLang.isChildOf(clazz, Timestamp.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    /**
     * Converts various object types into a java.sql.Date.
     */
    public static Date get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Date.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return Date.valueOf(LocalDate.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return Date.valueOf(LocalDate.now());
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return Date.valueOf(LocalDateTime.class.cast(value).toLocalDate());
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return Date.valueOf(ZonedDateTime.class.cast(value).toLocalDate());
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return Date.valueOf(OffsetDateTime.class.cast(value).toLocalDate());
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return Date.valueOf(LocalDate.now());
        }
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return new Date(Instant.class.cast(value).toEpochMilli());
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return new Date(java.util.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return new Date(Time.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return new Date(Timestamp.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    LocalDate parsed = format.parse(string, LocalDate::from);
                    return Date.valueOf(parsed);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return new Date(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to a java.sql.Date value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current date.
     */
    public static Date now() {
        return Date.valueOf(LocalDate.now());
    }

    /**
     * Adds days securely.
     */
    public static Date plusDays(Date date, long days) {
        if (date == null) return null;
        return Date.valueOf(date.toLocalDate().plusDays(days));
    }

    /**
     * Subtracts days securely.
     */
    public static Date minusDays(Date date, long days) {
        if (date == null) return null;
        return Date.valueOf(date.toLocalDate().minusDays(days));
    }

    /**
     * Adds months securely.
     */
    public static Date plusMonths(Date date, long months) {
        if (date == null) return null;
        return Date.valueOf(date.toLocalDate().plusMonths(months));
    }

    /**
     * Subtracts months securely.
     */
    public static Date minusMonths(Date date, long months) {
        if (date == null) return null;
        return Date.valueOf(date.toLocalDate().minusMonths(months));
    }

    /**
     * Adds years securely.
     */
    public static Date plusYears(Date date, long years) {
        if (date == null) return null;
        return Date.valueOf(date.toLocalDate().plusYears(years));
    }

    /**
     * Subtracts years securely.
     */
    public static Date minusYears(Date date, long years) {
        if (date == null) return null;
        return Date.valueOf(date.toLocalDate().minusYears(years));
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Checks if d1 is before d2 securely.
     */
    public static boolean isBefore(Date d1, Date d2) {
        if (d1 == null || d2 == null) return false;
        return d1.before(d2);
    }

    /**
     * Checks if d1 is after d2 securely.
     */
    public static boolean isAfter(Date d1, Date d2) {
        if (d1 == null || d2 == null) return false;
        return d1.after(d2);
    }

    /**
     * Checks if d1 is equal to d2 securely.
     */
    public static boolean isEqual(Date d1, Date d2) {
        if (d1 == null && d2 == null) return true;
        if (d1 == null || d2 == null) return false;
        return d1.toLocalDate().isEqual(d2.toLocalDate());
    }

    /**
     * Calculates the exact number of days between two dates.
     */
    public static Long daysBetween(Date startInclusive, Date endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return ChronoUnit.DAYS.between(startInclusive.toLocalDate(), endExclusive.toLocalDate());
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Default formatter (machine format yyyy-MM-dd).
     */
    public static String format(Date value) {
        if (value == null) return "";
        return WizInstant.formatDateMach.format(value.toLocalDate());
    }

    /**
     * Formats using the machine pattern (yyyy-MM-dd).
     */
    public static String formatDateMach(Date value) {
        if (value == null) return "";
        return WizInstant.formatDateMach.format(value.toLocalDate());
    }

    /**
     * Formats using the user pattern (dd/MM/yyyy).
     */
    public static String formatDateUser(Date value) {
        if (value == null) return "";
        return WizInstant.formatDateUser.format(value.toLocalDate());
    }

    /**
     * Formats using the file pattern (yyyy-MM-dd).
     */
    public static String formatDateFile(Date value) {
        if (value == null) return "";
        return WizInstant.formatDateFile.format(value.toLocalDate());
    }

}
