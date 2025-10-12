package br.com.pointel.jarch.mage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class WizUtilDate {

    private WizUtilDate() {
    }

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
                    return format.parse(string);
                }
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return new Date(Number.class.cast(value).longValue());
        }
        throw new Exception("Could not convert to a Date value the value of class: " + value.getClass().getName());
    }

    public static boolean is(String formatted, SimpleDateFormat onFormat) {
        return Objects.equals(
                WizString.replaceLettersOrDigits(formatted, 'x'), 
                WizString.replaceLettersOrDigits(onFormat.toPattern(), 'x'));
    }

    public static String format(Date value) {
        if (value == null) return "";
        return formatTimestampMach(value);
    }

    public static String formatDateUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateUser.format(value);
    }

    public static String formatTimeUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimeUser.format(value);
    }

    public static String formatMillisUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatMillisUser.format(value);
    }

    public static String formatDateTimeUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateTimeUser.format(value);
    }

    public static String formatTimestampUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimestampUser.format(value);
    }

    public static String formatInstantUser(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatInstantUser.format(value);
    }

    public static String formatDateMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateMach.format(value);
    }

    public static String formatTimeMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimeMach.format(value);
    }

    public static String formatMillisMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatMillisMach.format(value);
    }

    public static String formatDateTimeMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateTimeMach.format(value);
    }

    public static String formatTimestampMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimestampMach.format(value);
    }

    public static String formatInstantMach(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatInstantMach.format(value);
    }

    public static String formatDateFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateFile.format(value);
    }

    public static String formatTimeFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimeFile.format(value);
    }

    public static String formatMillisFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatMillisFile.format(value);
    }

    public static String formatDateTimeFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatDateTimeFile.format(value);
    }

    public static String formatTimestampFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatTimestampFile.format(value);
    }

    public static String formatInstantFile(Date value) {
        if (value == null) return "";
        return WizUtilDate.formatInstantFile.format(value);
    }

    public static Date parseDateUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatDateUser.parse(formatted);
    }

    public static Date parseTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatTimeUser.parse(formatted);
    }

    public static Date parseMillisUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatMillisUser.parse(formatted);
    }

    public static Date parseDateTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatDateTimeUser.parse(formatted);
    }

    public static Date parseTimestampUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatTimestampUser.parse(formatted);
    }

    public static Date parseInstantUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatInstantUser.parse(formatted);
    }

    public static Date parseDateMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatDateMach.parse(formatted);
    }

    public static Date parseTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatTimeMach.parse(formatted);
    }

    public static Date parseMillisMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatMillisMach.parse(formatted);
    }

    public static Date parseDateTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatDateTimeMach.parse(formatted);
    }

    public static Date parseTimestampMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatTimestampMach.parse(formatted);
    }

    public static Date parseInstantMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatInstantMach.parse(formatted);
    }

    public static Date parseDateFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatDateFile.parse(formatted);
    }

    public static Date parseTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatTimeFile.parse(formatted);
    }

    public static Date parseTimeMillisFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatMillisFile.parse(formatted);
    }

    public static Date parseDateTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatDateTimeFile.parse(formatted);
    }

    public static Date parseTimestampFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatTimestampFile.parse(formatted);
    }

    public static Date parseInstantFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizUtilDate.formatInstantFile.parse(formatted);
    }

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
    };
}
