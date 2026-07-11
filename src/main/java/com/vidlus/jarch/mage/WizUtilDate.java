package com.vidlus.jarch.mage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * A utility class for safely managing, parsing, and formatting legacy {@link java.util.Date} objects.
 * It provides thread-safe access to a large variety of predefined date and time formats.
 */
public class WizUtilDate {

    private WizUtilDate() {
    }

    /**
     * Checks if the given object is a Date, a modern java.time object, a SQL date/time object,
     * or a String/Number capable of representing a date.
     *
     * @param value the object to check
     * @return true if the object represents a date or time
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Date.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)
            || WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)
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
     * Intelligently parses and converts any date-like object into a standard {@link java.util.Date}.
     * Supports java.time API, SQL date types, Epoch numbers, and Strings formatted according
     * to the known internal formats.
     *
     * @param value the object to convert
     * @return a normalized Date, or null if the input is null
     * @throws Exception if the object cannot be converted
     */
    public static Date get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Date.class)) {
            return Date.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)) {
            return Date.from(java.time.LocalDate.class.cast(value).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)) {
            return Date.from(java.time.LocalTime.class.cast(value).atDate(java.time.LocalDate.now()).atZone(java.time.ZoneId.systemDefault()).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)) {
            return Date.from(java.time.LocalDateTime.class.cast(value).atZone(java.time.ZoneId.systemDefault()).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)) {
            return Date.from(java.time.ZonedDateTime.class.cast(value).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)) {
            return Date.from(java.time.OffsetDateTime.class.cast(value).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)) {
            return Date.from(java.time.OffsetTime.class.cast(value).atDate(java.time.LocalDate.now()).atZoneSameInstant(java.time.ZoneId.systemDefault()).toInstant());
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return java.util.Date.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return new Date(java.sql.Date.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Time.class)) {
            return new Date(java.sql.Time.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)) {
            return new Date(java.sql.Timestamp.class.cast(value).getTime());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            for (var format : WizUtilDate.getFormats()) {
                if (WizUtilDate.is(string, format)) {
                    synchronized (format) {
                        return format.parse(string);
                    }
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return new Date(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to a Date value the value of class: " + value.getClass().getName());
    }

    /**
     * Tests whether a given string matches the structural pattern of a specific SimpleDateFormat.
     * Note: Replaces all letters/digits with 'x' to compare shapes rather than actual parsing.
     *
     * @param formatted the string to check
     * @param onFormat  the format to check against
     * @return true if the shapes match
     */
    public static boolean is(String formatted, SimpleDateFormat onFormat) {
        return Objects.equals(
                WizString.replaceLettersOrDigits(formatted, 'x'), 
                WizString.replaceLettersOrDigits(onFormat.toPattern(), 'x'));
    }

    // =========================================================================
    // DATE MANIPULATION (MATH & LOGIC)
    // =========================================================================

    /**
     * @return the current Date representing right now
     */
    public static Date now() {
        return new Date();
    }

    /**
     * Adds (or subtracts) a specific number of years to a Date.
     *
     * @param date   the source date
     * @param amount the number of years to add (negative to subtract)
     * @return a new Date with the time shifted
     */
    public static Date addYears(Date date, int amount) {
        return shift(date, Calendar.YEAR, amount);
    }

    /**
     * Adds (or subtracts) a specific number of months to a Date.
     *
     * @param date   the source date
     * @param amount the number of months to add (negative to subtract)
     * @return a new Date with the time shifted
     */
    public static Date addMonths(Date date, int amount) {
        return shift(date, Calendar.MONTH, amount);
    }

    /**
     * Adds (or subtracts) a specific number of days to a Date.
     *
     * @param date   the source date
     * @param amount the number of days to add (negative to subtract)
     * @return a new Date with the time shifted
     */
    public static Date addDays(Date date, int amount) {
        return shift(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * Adds (or subtracts) a specific number of hours to a Date.
     *
     * @param date   the source date
     * @param amount the number of hours to add (negative to subtract)
     * @return a new Date with the time shifted
     */
    public static Date addHours(Date date, int amount) {
        return shift(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * Adds (or subtracts) a specific number of minutes to a Date.
     *
     * @param date   the source date
     * @param amount the number of minutes to add (negative to subtract)
     * @return a new Date with the time shifted
     */
    public static Date addMinutes(Date date, int amount) {
        return shift(date, Calendar.MINUTE, amount);
    }

    /**
     * Adds (or subtracts) a specific number of seconds to a Date.
     *
     * @param date   the source date
     * @param amount the number of seconds to add (negative to subtract)
     * @return a new Date with the time shifted
     */
    public static Date addSeconds(Date date, int amount) {
        return shift(date, Calendar.SECOND, amount);
    }

    private static Date shift(Date date, int calendarField, int amount) {
        if (date == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * Null-safe comparison to check if one date is strictly before another.
     *
     * @param d1 the first date
     * @param d2 the second date
     * @return true if d1 occurs before d2
     */
    public static boolean isBefore(Date d1, Date d2) {
        if (d1 == null || d2 == null) return false;
        return d1.before(d2);
    }

    /**
     * Null-safe comparison to check if one date is strictly after another.
     *
     * @param d1 the first date
     * @param d2 the second date
     * @return true if d1 occurs after d2
     */
    public static boolean isAfter(Date d1, Date d2) {
        if (d1 == null || d2 == null) return false;
        return d1.after(d2);
    }

    // =========================================================================
    // SYNCHRONIZED FORMATTERS
    // Note: SimpleDateFormat is not thread-safe. All formatting accesses below
    // are synchronized to prevent race conditions during heavy multi-threading.
    // =========================================================================

    public static synchronized String format(Date value) {
        if (value == null) return "";
        return formatTimestampMach(value);
    }

    public static synchronized String formatDateUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateUser.format(value);
    }

    public static synchronized String formatTimeUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimeUser.format(value);
    }

    public static synchronized String formatMillisUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatMillisUser.format(value);
    }

    public static synchronized String formatDateTimeUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateTimeUser.format(value);
    }

    public static synchronized String formatTimestampUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimestampUser.format(value);
    }

    public static synchronized String formatInstantUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatInstantUser.format(value);
    }

    public static synchronized String formatDateMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateMach.format(value);
    }

    public static synchronized String formatTimeMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimeMach.format(value);
    }

    public static synchronized String formatMillisMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatMillisMach.format(value);
    }

    public static synchronized String formatDateTimeMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateTimeMach.format(value);
    }

    public static synchronized String formatTimestampMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimestampMach.format(value);
    }

    public static synchronized String formatInstantMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatInstantMach.format(value);
    }

    public static synchronized String formatDateFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateFile.format(value);
    }

    public static synchronized String formatTimeFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimeFile.format(value);
    }

    public static synchronized String formatMillisFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatMillisFile.format(value);
    }

    public static synchronized String formatDateTimeFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateTimeFile.format(value);
    }

    public static synchronized String formatTimestampFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimestampFile.format(value);
    }

    public static synchronized String formatInstantFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatInstantFile.format(value);
    }

    // =========================================================================
    // SYNCHRONIZED PARSERS
    // =========================================================================

    public static synchronized Date parseDateUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateUser.parse(formatted);
    }

    public static synchronized Date parseTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimeUser.parse(formatted);
    }

    public static synchronized Date parseMillisUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatMillisUser.parse(formatted);
    }

    public static synchronized Date parseDateTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateTimeUser.parse(formatted);
    }

    public static synchronized Date parseTimestampUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimestampUser.parse(formatted);
    }

    public static synchronized Date parseInstantUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatInstantUser.parse(formatted);
    }

    public static synchronized Date parseDateMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateMach.parse(formatted);
    }

    public static synchronized Date parseTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimeMach.parse(formatted);
    }

    public static synchronized Date parseMillisMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatMillisMach.parse(formatted);
    }

    public static synchronized Date parseDateTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateTimeMach.parse(formatted);
    }

    public static synchronized Date parseTimestampMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimestampMach.parse(formatted);
    }

    public static synchronized Date parseInstantMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatInstantMach.parse(formatted);
    }

    public static synchronized Date parseDateFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateFile.parse(formatted);
    }

    public static synchronized Date parseTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimeFile.parse(formatted);
    }

    public static synchronized Date parseTimeMillisFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatMillisFile.parse(formatted);
    }

    public static synchronized Date parseDateTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatDateTimeFile.parse(formatted);
    }

    public static synchronized Date parseTimestampFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatTimestampFile.parse(formatted);
    }

    public static synchronized Date parseInstantFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) return null;
        return WizUtilDate.formatInstantFile.parse(formatted);
    }

    // =========================================================================
    // RAW FORMATTERS (Caution: Not intrinsically thread-safe if used directly)
    // =========================================================================

    public static SimpleDateFormat formatDateUser = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat formatTimeUser = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat formatMillisUser = new SimpleDateFormat("HH:mm:ss SSS");
    public static SimpleDateFormat formatDateTimeUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static SimpleDateFormat formatTimestampUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss SSS");
    public static SimpleDateFormat formatInstantUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss SSS Z");

    public static SimpleDateFormat formatDateMach = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat formatTimeMach = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat formatMillisMach = new SimpleDateFormat("HH:mm:ss.SSS");
    public static SimpleDateFormat formatDateTimeMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat formatTimestampMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static SimpleDateFormat formatInstantMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");

    public static SimpleDateFormat formatDateFile = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat formatTimeFile = new SimpleDateFormat("HH-mm-ss");
    public static SimpleDateFormat formatMillisFile = new SimpleDateFormat("HH-mm-ss-SSS");
    public static SimpleDateFormat formatDateTimeFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    public static SimpleDateFormat formatTimestampFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
    public static SimpleDateFormat formatInstantFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS-Z");

    public static SimpleDateFormat[] getFormats() {
        return new SimpleDateFormat[] {
                WizUtilDate.formatDateMach,
                WizUtilDate.formatTimeMach,
                WizUtilDate.formatMillisMach,
                WizUtilDate.formatDateTimeMach,
                WizUtilDate.formatTimestampMach,
                WizUtilDate.formatInstantMach,
                WizUtilDate.formatDateUser,
                WizUtilDate.formatTimeUser,
                WizUtilDate.formatMillisUser,
                WizUtilDate.formatDateTimeUser,
                WizUtilDate.formatTimestampUser,
                WizUtilDate.formatInstantUser,
                WizUtilDate.formatDateFile,
                WizUtilDate.formatTimeFile,
                WizUtilDate.formatMillisFile,
                WizUtilDate.formatDateTimeFile,
                WizUtilDate.formatTimestampFile,
                WizUtilDate.formatInstantFile
        };
    }
}
