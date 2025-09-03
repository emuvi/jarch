package br.com.pointel.jarch.mage;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class WizInstant {

    private WizInstant() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        Class<?> clazz = value.getClass();
        return WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, java.time.LocalDate.class)
            || WizLang.isChildOf(clazz, java.time.LocalTime.class)
            || WizLang.isChildOf(clazz, java.time.LocalDateTime.class)
            || WizLang.isChildOf(clazz, java.time.ZonedDateTime.class)
            || WizLang.isChildOf(clazz, java.time.OffsetDateTime.class)
            || WizLang.isChildOf(clazz, java.time.OffsetTime.class)
            || WizLang.isChildOf(clazz, java.util.Date.class)
            || WizLang.isChildOf(clazz, java.sql.Date.class)
            || WizLang.isChildOf(clazz, java.sql.Time.class)
            || WizLang.isChildOf(clazz, java.sql.Timestamp.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Number.class);
    }

    public static Instant get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Instant.class)) {
            return Instant.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDate.class)) {
            return java.time.LocalDate.class.cast(value).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalTime.class)) {
            return java.time.LocalTime.class.cast(value).atDate(java.time.LocalDate.now()).atZone(java.time.ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.LocalDateTime.class)) {
            return java.time.LocalDateTime.class.cast(value).atZone(java.time.ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.ZonedDateTime.class)) {
            return java.time.ZonedDateTime.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetDateTime.class)) {
            return java.time.OffsetDateTime.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), java.time.OffsetTime.class)) {
            return java.time.OffsetTime.class.cast(value).atDate(java.time.LocalDate.now()).atZoneSameInstant(java.time.ZoneId.systemDefault()).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), java.util.Date.class)) {
            return java.util.Date.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Date.class)) {
            return java.sql.Date.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Time.class)) {
            return java.sql.Time.class.cast(value).toInstant();
        }
        if (WizLang.isChildOf(value.getClass(), java.sql.Timestamp.class)) {
            return java.sql.Timestamp.class.cast(value).toInstant();
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

    public static boolean is(String formatted, DateTimeFormatter onFormat) {
        return Objects.equals(
                WizString.replaceLettersOrDigits(formatted, 'x'),
                WizString.replaceLettersOrDigits(onFormat.toString(), 'x'));
    }

    public static String format(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatInstantMach(instant);
    }

    public static String formatDateUser(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatDateUser.format(instant);
    }

    public static String formatTimeUser(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatTimeUser.format(instant);
    }

    public static String formatMillisUser(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatMillisUser.format(instant);
    }

    public static String formatDateTimeUser(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatDateTimeUser.format(instant);
    }

    public static String formatTimestampUser(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatTimestampUser.format(instant);
    }

    public static String formatInstantUser(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatInstantUser.format(instant);
    }

    public static String formatDateMach(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatDateMach.format(instant);
    }

    public static String formatTimeMach(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatTimeMach.format(instant);
    }

    public static String formatMillisMach(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatMillisMach.format(instant);
    }

    public static String formatDateTimeMach(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatDateTimeMach.format(instant);
    }

    public static String formatTimestampMach(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatTimestampMach.format(instant);
    }

    public static String formatInstantMach(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatInstantMach.format(instant);
    }

    public static String formatDateFile(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatDateFile.format(instant);
    }

    public static String formatTimeFile(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatTimeFile.format(instant);
    }

    public static String formatMillisFile(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatMillisFile.format(instant);
    }

    public static String formatDateTimeFile(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatDateTimeFile.format(instant);
    }

    public static String formatTimestampFile(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatTimestampFile.format(instant);
    }

    public static String formatInstantFile(Instant instant) {
        if (instant == null) {
            return "";
        }
        return WizInstant.formatInstantFile.format(instant);
    }

    public static Instant parseDateUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateUser.parse(formatted, Instant::from);
    }

    public static Instant parseTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimeUser.parse(formatted, Instant::from);
    }

    public static Instant parseMillisUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatMillisUser.parse(formatted, Instant::from);
    }

    public static Instant parseDateTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateTimeUser.parse(formatted, Instant::from);
    }

    public static Instant parseTimestampUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimestampUser.parse(formatted, Instant::from);
    }

    public static Instant parseInstantUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatInstantUser.parse(formatted, Instant::from);
    }

    public static Instant parseDateMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateMach.parse(formatted, Instant::from);
    }

    public static Instant parseTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimeMach.parse(formatted, Instant::from);
    }

    public static Instant parseMillisMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatMillisMach.parse(formatted, Instant::from);
    }

    public static Instant parseDateTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateTimeMach.parse(formatted, Instant::from);
    }

    public static Instant parseTimestampMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimestampMach.parse(formatted, Instant::from);
    }

    public static Instant parseInstantMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatInstantMach.parse(formatted, Instant::from);
    }

    public static Instant parseDateFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateFile.parse(formatted, Instant::from);
    }

    public static Instant parseTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimeFile.parse(formatted, Instant::from);
    }

    public static Instant parseTimeMillisFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatMillisFile.parse(formatted, Instant::from);
    }

    public static Instant parseDateTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatDateTimeFile.parse(formatted, Instant::from);
    }

    public static Instant parseTimestampFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatTimestampFile.parse(formatted, Instant::from);
    }

    public static Instant parseInstantFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizInstant.formatInstantFile.parse(formatted, Instant::from);
    }

    public static final DateTimeFormatter formatDateUser = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter formatTimeUser = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter formatMillisUser = DateTimeFormatter.ofPattern("HH:mm:ss ZZZ");
    public static final DateTimeFormatter formatDateTimeUser = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final DateTimeFormatter formatTimestampUser = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss ZZZ");
    public static final DateTimeFormatter formatInstantUser = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss ZZZ Z");

    public static final DateTimeFormatter formatDateMach = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter formatTimeMach = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter formatMillisMach = DateTimeFormatter.ofPattern("HH:mm:ss.ZZZ");
    public static final DateTimeFormatter formatDateTimeMach = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter formatTimestampMach = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.ZZZ");
    public static final DateTimeFormatter formatInstantMach = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.ZZZ Z");

    public static final DateTimeFormatter formatDateFile = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter formatTimeFile = DateTimeFormatter.ofPattern("HH-mm-ss");
    public static final DateTimeFormatter formatMillisFile = DateTimeFormatter.ofPattern("HH-mm-ss-ZZZ");
    public static final DateTimeFormatter formatDateTimeFile = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    public static final DateTimeFormatter formatTimestampFile = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-ZZZ");
    public static final DateTimeFormatter formatInstantFile = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-ZZZ-Z");

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
