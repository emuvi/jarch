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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;

public class WizInstant {

    private WizInstant() {
    }

    /**
     * Checks if the given value can be converted to an Instant.
     * Supports Instant, date/time types, Date, Timestamp, String, and Number.
     *
     * @param value the value to check
     * @return true if the value can be converted, false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, Date.class)
            || WizLang.isChildOf(clazz, java.sql.Date.class)
            || WizLang.isChildOf(clazz, Time.class)
            || WizLang.isChildOf(clazz, Timestamp.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    /**
     * Converts the given value to an Instant.
     * Handles Instant, LocalDate, LocalTime, LocalDateTime, ZonedDateTime, OffsetDateTime, OffsetTime,
     * Date, java.sql.Date, Time, Timestamp, String (with various formats), and Number (as epoch millis).
     *
     * @param value the value to convert
     * @return the converted Instant, or null if value is null
     * @throws Exception if the value cannot be converted
     */
    public static Instant get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), LocalDate.class)) {
            return LocalDate.class.cast(value).atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), LocalTime.class)) {
            return LocalTime.class.cast(value).atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), LocalDateTime.class)) {
            return LocalDateTime.class.cast(value).atZone(ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), ZonedDateTime.class)) {
            return ZonedDateTime.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetDateTime.class)) {
            return OffsetDateTime.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), OffsetTime.class)) {
            return OffsetTime.class.cast(value).atDate(LocalDate.now()).atZoneSameInstant(ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Instant.ofEpochMilli(Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), Time.class)) {
            return Time.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), Timestamp.class)) {
            return Timestamp.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizInstant.getFormats()) {
                if (WizInstant.is(string, format)) {
                    return format.parse(string, Instant::from);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Instant.ofEpochMilli(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to an Instant value the value of class: " + value.getClass().getName());
    }

    // =========================================================================
    // CREATION & MANIPULATION
    // =========================================================================

    /**
     * Gets the current time as an Instant.
     *
     * @return the current Instant
     */
    public static Instant now() {
        return Instant.now();
    }

    /**
     * Adds a time amount to an Instant.
     *
     * @param instant the Instant to add to (null-safe)
     * @param amount the amount to add
     * @param unit the temporal unit (e.g., ChronoUnit.DAYS)
     * @return the modified Instant, or null if input is null
     */
    public static Instant plus(Instant instant, long amount, TemporalUnit unit) {
        if (instant == null) return null;
        return instant.plus(amount, unit);
    }

    /**
     * Subtracts a time amount from an Instant.
     *
     * @param instant the Instant to subtract from (null-safe)
     * @param amount the amount to subtract
     * @param unit the temporal unit (e.g., ChronoUnit.DAYS)
     * @return the modified Instant, or null if input is null
     */
    public static Instant minus(Instant instant, long amount, TemporalUnit unit) {
        if (instant == null) return null;
        return instant.minus(amount, unit);
    }

    /**
     * Adds days to an Instant.
     *
     * @param instant the Instant to add to (null-safe)
     * @param days the number of days to add
     * @return the modified Instant, or null if input is null
     */
    public static Instant plusDays(Instant instant, long days) {
        return plus(instant, days, ChronoUnit.DAYS);
    }

    /**
     * Subtracts days from an Instant.
     *
     * @param instant the Instant to subtract from (null-safe)
     * @param days the number of days to subtract
     * @return the modified Instant, or null if input is null
     */
    public static Instant minusDays(Instant instant, long days) {
        return minus(instant, days, ChronoUnit.DAYS);
    }

    /**
     * Adds hours to an Instant.
     *
     * @param instant the Instant to add to (null-safe)
     * @param hours the number of hours to add
     * @return the modified Instant, or null if input is null
     */
    public static Instant plusHours(Instant instant, long hours) {
        return plus(instant, hours, ChronoUnit.HOURS);
    }

    /**
     * Subtracts hours from an Instant.
     *
     * @param instant the Instant to subtract from (null-safe)
     * @param hours the number of hours to subtract
     * @return the modified Instant, or null if input is null
     */
    public static Instant minusHours(Instant instant, long hours) {
        return minus(instant, hours, ChronoUnit.HOURS);
    }

    /**
     * Adds minutes to an Instant.
     *
     * @param instant the Instant to add to (null-safe)
     * @param minutes the number of minutes to add
     * @return the modified Instant, or null if input is null
     */
    public static Instant plusMinutes(Instant instant, long minutes) {
        return plus(instant, minutes, ChronoUnit.MINUTES);
    }

    /**
     * Subtracts minutes from an Instant.
     *
     * @param instant the Instant to subtract from (null-safe)
     * @param minutes the number of minutes to subtract
     * @return the modified Instant, or null if input is null
     */
    public static Instant minusMinutes(Instant instant, long minutes) {
        return minus(instant, minutes, ChronoUnit.MINUTES);
    }

    /**
     * Truncates an Instant to days (removes time portion).
     *
     * @param instant the Instant to truncate (null-safe)
     * @return the truncated Instant, or null if input is null
     */
    public static Instant truncateToDays(Instant instant) {
        if (instant == null) return null;
        return instant.truncatedTo(ChronoUnit.DAYS);
    }

    /**
     * Truncates an Instant to hours (removes minutes/seconds/nanos).
     *
     * @param instant the Instant to truncate (null-safe)
     * @return the truncated Instant, or null if input is null
     */
    public static Instant truncateToHours(Instant instant) {
        if (instant == null) return null;
        return instant.truncatedTo(ChronoUnit.HOURS);
    }

    // =========================================================================
    // COMPARISONS & DURATIONS
    // =========================================================================

    /**
     * Checks if the first Instant is before the second.
     *
     * @param i1 the first Instant
     * @param i2 the second Instant
     * @return true if i1 is before i2; false if either is null
     */
    public static boolean isBefore(Instant i1, Instant i2) {
        if (i1 == null || i2 == null) return false;
        return i1.isBefore(i2);
    }

    /**
     * Checks if the first Instant is after the second.
     *
     * @param i1 the first Instant
     * @param i2 the second Instant
     * @return true if i1 is after i2; false if either is null
     */
    public static boolean isAfter(Instant i1, Instant i2) {
        if (i1 == null || i2 == null) return false;
        return i1.isAfter(i2);
    }

    /**
     * Checks if two Instants are equal.
     *
     * @param i1 the first Instant
     * @param i2 the second Instant
     * @return true if equal; both null is considered equal
     */
    public static boolean isEqual(Instant i1, Instant i2) {
        if (i1 == null && i2 == null) return true;
        if (i1 == null || i2 == null) return false;
        return i1.equals(i2);
    }

    /**
     * Calculates the Duration between two Instants.
     *
     * @param startInclusive the start Instant (inclusive)
     * @param endExclusive the end Instant (exclusive)
     * @return the Duration between them, or null if either is null
     */
    public static Duration between(Instant startInclusive, Instant endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return Duration.between(startInclusive, endExclusive);
    }

    /**
     * Calculates the number of days between two Instants.
     *
     * @param startInclusive the start Instant (inclusive)
     * @param endExclusive the end Instant (exclusive)
     * @return the number of days between them, or null if either is null
     */
    public static Long daysBetween(Instant startInclusive, Instant endExclusive) {
        if (startInclusive == null || endExclusive == null) return null;
        return ChronoUnit.DAYS.between(startInclusive, endExclusive);
    }

    // =========================================================================
    // FORMATTING
    // =========================================================================

    /**
     * Checks if a string matches a DateTimeFormatter pattern.
     *
     * @param formatted the formatted string
     * @param onFormat the DateTimeFormatter to check against
     * @return true if the string matches the pattern
     */
    public static boolean is(String formatted, DateTimeFormatter onFormat) {
        return Objects.equals(
                WizString.replaceLettersOrDigits(formatted, 'x'),
                WizString.replaceLettersOrDigits(onFormat.toString(), 'x'));
    }

    /**
     * Formats an Instant as a string using the machine-readable instant format.
     *
     * @param value the Instant to format
     * @return the formatted string, or empty string if null
     */
    public static String format(Instant value) {
        if (value == null) return "";
        return WizInstant.formatInstantMach(value);
    }

    /**
     * Formats an Instant as a date string in user-readable format (dd/MM/yyyy).
     *
     * @param value the Instant to format
     * @return the formatted date string, or empty string if null
     */
    public static String formatDateUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateUser.format(value);
    }

    /**
     * Formats an Instant as a time string in user-readable format (HH:mm:ss).
     *
     * @param value the Instant to format
     * @return the formatted time string, or empty string if null
     */
    public static String formatTimeUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimeUser.format(value);
    }

    /**
     * Formats an Instant as a time string with milliseconds in user-readable format (HH:mm:ss SSS).
     *
     * @param value the Instant to format
     * @return the formatted time string, or empty string if null
     */
    public static String formatMillisUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatMillisUser.format(value);
    }

    /**
     * Formats an Instant as a date-time string in user-readable format (dd/MM/yyyy HH:mm:ss).
     *
     * @param value the Instant to format
     * @return the formatted date-time string, or empty string if null
     */
    public static String formatDateTimeUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeUser.format(value);
    }

    /**
     * Formats an Instant as a timestamp string in user-readable format (dd/MM/yyyy HH:mm:ss SSS).
     *
     * @param value the Instant to format
     * @return the formatted timestamp string, or empty string if null
     */
    public static String formatTimestampUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimestampUser.format(value);
    }

    /**
     * Formats an Instant with zone offset in user-readable format (dd/MM/yyyy HH:mm:ss SSS Z).
     *
     * @param value the Instant to format
     * @return the formatted instant string, or empty string if null
     */
    public static String formatInstantUser(Instant value) {
        if (value == null) return "";
        return WizInstant.formatInstantUser.format(value);
    }

    /**
     * Formats an Instant as a date string in machine-readable format (yyyy-MM-dd).
     *
     * @param value the Instant to format
     * @return the formatted date string, or empty string if null
     */
    public static String formatDateMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateMach.format(value);
    }

    /**
     * Formats an Instant as a time string in machine-readable format (HH:mm:ss).
     *
     * @param value the Instant to format
     * @return the formatted time string, or empty string if null
     */
    public static String formatTimeMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimeMach.format(value);
    }

    /**
     * Formats an Instant as a time string with milliseconds in machine-readable format (HH:mm:ss.SSS).
     *
     * @param value the Instant to format
     * @return the formatted time string, or empty string if null
     */
    public static String formatMillisMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatMillisMach.format(value);
    }

    /**
     * Formats an Instant as a date-time string in machine-readable format (yyyy-MM-dd HH:mm:ss).
     *
     * @param value the Instant to format
     * @return the formatted date-time string, or empty string if null
     */
    public static String formatDateTimeMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeMach.format(value);
    }

    /**
     * Formats an Instant as a timestamp string in machine-readable format (yyyy-MM-dd HH:mm:ss.SSS).
     *
     * @param value the Instant to format
     * @return the formatted timestamp string, or empty string if null
     */
    public static String formatTimestampMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimestampMach.format(value);
    }

    /**
     * Formats an Instant with zone offset in machine-readable format (yyyy-MM-dd HH:mm:ss.SSS Z).
     *
     * @param value the Instant to format
     * @return the formatted instant string, or empty string if null
     */
    public static String formatInstantMach(Instant value) {
        if (value == null) return "";
        return WizInstant.formatInstantMach.format(value);
    }

    /**
     * Formats an Instant as a date string in file-compatible format (yyyy-MM-dd).
     *
     * @param value the Instant to format
     * @return the formatted date string, or empty string if null
     */
    public static String formatDateFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateFile.format(value);
    }

    /**
     * Formats an Instant as a time string in file-compatible format (HH-mm-ss).
     *
     * @param value the Instant to format
     * @return the formatted time string, or empty string if null
     */
    public static String formatTimeFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimeFile.format(value);
    }

    /**
     * Formats an Instant as a time string with milliseconds in file-compatible format (HH-mm-ss-SSS).
     *
     * @param value the Instant to format
     * @return the formatted time string, or empty string if null
     */
    public static String formatMillisFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatMillisFile.format(value);
    }

    /**
     * Formats an Instant as a date-time string in file-compatible format (yyyy-MM-dd-HH-mm-ss).
     *
     * @param value the Instant to format
     * @return the formatted date-time string, or empty string if null
     */
    public static String formatDateTimeFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatDateTimeFile.format(value);
    }

    /**
     * Formats an Instant as a timestamp string in file-compatible format (yyyy-MM-dd-HH-mm-ss-SSS).
     *
     * @param value the Instant to format
     * @return the formatted timestamp string, or empty string if null
     */
    public static String formatTimestampFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatTimestampFile.format(value);
    }

    /**
     * Formats an Instant with zone offset in file-compatible format (yyyy-MM-dd-HH-mm-ss-SSS-Z).
     *
     * @param value the Instant to format
     * @return the formatted instant string, or empty string if null
     */
    public static String formatInstantFile(Instant value) {
        if (value == null) return "";
        return WizInstant.formatInstantFile.format(value);
    }

    /**
     * Parses a user-readable date string (dd/MM/yyyy) into an Instant.
     *
     * @param formatted the formatted date string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a user-readable time string (HH:mm:ss) into an Instant.
     *
     * @param formatted the formatted time string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimeUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a user-readable time string with milliseconds (HH:mm:ss SSS) into an Instant.
     *
     * @param formatted the formatted time string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseMillisUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatMillisUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a user-readable date-time string (dd/MM/yyyy HH:mm:ss) into an Instant.
     *
     * @param formatted the formatted date-time string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateTimeUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a user-readable timestamp string (dd/MM/yyyy HH:mm:ss SSS) into an Instant.
     *
     * @param formatted the formatted timestamp string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimestampUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimestampUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a user-readable instant string with zone (dd/MM/yyyy HH:mm:ss SSS Z) into an Instant.
     *
     * @param formatted the formatted instant string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseInstantUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatInstantUser.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable date string (yyyy-MM-dd) into an Instant.
     *
     * @param formatted the formatted date string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable time string (HH:mm:ss) into an Instant.
     *
     * @param formatted the formatted time string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimeMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable time string with milliseconds (HH:mm:ss.SSS) into an Instant.
     *
     * @param formatted the formatted time string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseMillisMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatMillisMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable date-time string (yyyy-MM-dd HH:mm:ss) into an Instant.
     *
     * @param formatted the formatted date-time string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateTimeMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable timestamp string (yyyy-MM-dd HH:mm:ss.SSS) into an Instant.
     *
     * @param formatted the formatted timestamp string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimestampMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimestampMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a machine-readable instant string with zone (yyyy-MM-dd HH:mm:ss.SSS Z) into an Instant.
     *
     * @param formatted the formatted instant string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseInstantMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatInstantMach.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible date string (yyyy-MM-dd) into an Instant.
     *
     * @param formatted the formatted date string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateFile.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible time string (HH-mm-ss) into an Instant.
     *
     * @param formatted the formatted time string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimeFile.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible time string with milliseconds (HH-mm-ss-SSS) into an Instant.
     *
     * @param formatted the formatted time string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimeMillisFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatMillisFile.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible date-time string (yyyy-MM-dd-HH-mm-ss) into an Instant.
     *
     * @param formatted the formatted date-time string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseDateTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateTimeFile.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible timestamp string (yyyy-MM-dd-HH-mm-ss-SSS) into an Instant.
     *
     * @param formatted the formatted timestamp string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseTimestampFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimestampFile.parse(formatted, Instant::from);
    }

    /**
     * Parses a file-compatible instant string with zone (yyyy-MM-dd-HH-mm-ss-SSS-Z) into an Instant.
     *
     * @param formatted the formatted instant string
     * @return the parsed Instant, or null if formatted is null or empty
     * @throws Exception if parsing fails
     */
    public static Instant parseInstantFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatInstantFile.parse(formatted, Instant::from);
    }

    // Formatter constants for user-readable formats
    /**
     * DateTimeFormatter for user-readable dates (dd/MM/yyyy).
     */
    public static final DateTimeFormatter formatDateUser = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    /**
     * DateTimeFormatter for user-readable times (HH:mm:ss).
     */
    public static final DateTimeFormatter formatTimeUser = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * DateTimeFormatter for user-readable times with milliseconds (HH:mm:ss SSS).
     */
    public static final DateTimeFormatter formatMillisUser = DateTimeFormatter.ofPattern("HH:mm:ss SSS");
    /**
     * DateTimeFormatter for user-readable date-time (dd/MM/yyyy HH:mm:ss).
     */
    public static final DateTimeFormatter formatDateTimeUser = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    /**
     * DateTimeFormatter for user-readable timestamp with milliseconds (dd/MM/yyyy HH:mm:ss SSS).
     */
    public static final DateTimeFormatter formatTimestampUser = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss SSS");
    /**
     * DateTimeFormatter for user-readable instant with zone offset (dd/MM/yyyy HH:mm:ss SSS Z).
     */
    public static final DateTimeFormatter formatInstantUser = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss SSS Z");

    // Formatter constants for machine-readable formats
    /**
     * DateTimeFormatter for machine-readable dates (yyyy-MM-dd).
     */
    public static final DateTimeFormatter formatDateMach = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * DateTimeFormatter for machine-readable times (HH:mm:ss).
     */
    public static final DateTimeFormatter formatTimeMach = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * DateTimeFormatter for machine-readable times with milliseconds (HH:mm:ss.SSS).
     */
    public static final DateTimeFormatter formatMillisMach = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    /**
     * DateTimeFormatter for machine-readable date-time (yyyy-MM-dd HH:mm:ss).
     */
    public static final DateTimeFormatter formatDateTimeMach = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * DateTimeFormatter for machine-readable timestamp with milliseconds (yyyy-MM-dd HH:mm:ss.SSS).
     */
    public static final DateTimeFormatter formatTimestampMach = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    /**
     * DateTimeFormatter for machine-readable instant with zone offset (yyyy-MM-dd HH:mm:ss.SSS Z).
     */
    public static final DateTimeFormatter formatInstantMach = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");

    // Formatter constants for file-compatible formats
    /**
     * DateTimeFormatter for file-compatible dates (yyyy-MM-dd).
     */
    public static final DateTimeFormatter formatDateFile = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * DateTimeFormatter for file-compatible times (HH-mm-ss).
     */
    public static final DateTimeFormatter formatTimeFile = DateTimeFormatter.ofPattern("HH-mm-ss");
    /**
     * DateTimeFormatter for file-compatible times with milliseconds (HH-mm-ss-SSS).
     */
    public static final DateTimeFormatter formatMillisFile = DateTimeFormatter.ofPattern("HH-mm-ss-SSS");
    /**
     * DateTimeFormatter for file-compatible date-time (yyyy-MM-dd-HH-mm-ss).
     */
    public static final DateTimeFormatter formatDateTimeFile = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    /**
     * DateTimeFormatter for file-compatible timestamp with milliseconds (yyyy-MM-dd-HH-mm-ss-SSS).
     */
    public static final DateTimeFormatter formatTimestampFile = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
    /**
     * DateTimeFormatter for file-compatible instant with zone offset (yyyy-MM-dd-HH-mm-ss-SSS-Z).
     */
    public static final DateTimeFormatter formatInstantFile = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS-Z");

    /**
     * Gets all supported DateTimeFormatters for trying different format patterns.
     * Includes user-readable, machine-readable, and file-compatible formats.
     *
     * @return an array of all DateTimeFormatters
     */
    public static DateTimeFormatter[] getFormats() {
        return new DateTimeFormatter[] {
                WizInstant.formatDateMach,
                WizInstant.formatTimeMach,
                WizInstant.formatMillisMach,
                WizInstant.formatDateTimeMach,
                WizInstant.formatTimestampMach,
                WizInstant.formatInstantMach,
                WizInstant.formatDateUser,
                WizInstant.formatTimeUser,
                WizInstant.formatMillisUser,
                WizInstant.formatDateTimeUser,
                WizInstant.formatTimestampUser,
                WizInstant.formatInstantUser,
                WizInstant.formatDateFile,
                WizInstant.formatTimeFile,
                WizInstant.formatMillisFile,
                WizInstant.formatDateTimeFile,
                WizInstant.formatTimestampFile,
                WizInstant.formatInstantFile
        };
    };

}
