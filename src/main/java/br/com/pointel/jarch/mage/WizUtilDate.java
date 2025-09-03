package br.com.pointel.jarch.mage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class WizUtilDate {

    public static Date get(Object data) throws Exception {
        if (data == null) {
            return null;
        }
        if (WizLang.isChildOf(data.getClass(), Date.class)) {
            return Date.class.cast(data);
        }
        if (data instanceof java.time.LocalDate localDate) {
            return Date.from(localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
        } else if (data instanceof java.time.LocalTime localTime) {
            return Date.from(localTime.atDate(java.time.LocalDate.now()).atZone(java.time.ZoneId.systemDefault()).toInstant());
        } else if (data instanceof java.time.LocalDateTime localDateTime) {
            return Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
        } else if (data instanceof java.time.ZonedDateTime zonedDateTime) {
            return Date.from(zonedDateTime.toInstant());
        } else if (data instanceof java.time.OffsetDateTime offsetDateTime) {
            return Date.from(offsetDateTime.toInstant());
        } else if (data instanceof java.time.OffsetTime offsetTime) {
            return Date.from(offsetTime.atDate(java.time.LocalDate.now()).atZoneSameInstant(java.time.ZoneId.systemDefault()).toInstant());
        } else if (data instanceof java.time.Instant instant) {
            return Date.from(instant);
        } else if (data instanceof java.sql.Date date) {
            return new Date(date.getTime());
        } else if (data instanceof java.sql.Time time) {
            return new Date(time.getTime());
        } else if (data instanceof java.sql.Timestamp timestamp) {
            return new Date(timestamp.getTime());
        } else if (data instanceof String formatted) {
            for (var format : WizUtilDate.getFormats()) {
                if (WizUtilDate.is(formatted, format)) {
                    return format.parse(formatted);
                }
            }
        }
        throw new Exception("Could not convert to a Date value the value of class: " + data.getClass().getName());
    }

    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        return formatTimestampMach(date);
    }

    public static boolean is(String formatted, SimpleDateFormat onFormat) {
        return Objects.equals(
                WizChars.replaceLettersOrDigits(formatted, 'x'), 
                WizChars.replaceLettersOrDigits(onFormat.toPattern(), 'x'));
    }

    public static String formatDateUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatDateUser.format(date);
    }

    public static String formatTimeUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatTimeUser.format(date);
    }

    public static String formatMillisUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatMillisUser.format(date);
    }

    public static String formatDateTimeUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatDateTimeUser.format(date);
    }

    public static String formatTimestampUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatTimestampUser.format(date);
    }

    public static String formatInstantUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatInstantUser.format(date);
    }

    public static String formatDateMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatDateMach.format(date);
    }

    public static String formatTimeMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatTimeMach.format(date);
    }

    public static String formatMillisMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatMillisMach.format(date);
    }

    public static String formatDateTimeMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatDateTimeMach.format(date);
    }

    public static String formatTimestampMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatTimestampMach.format(date);
    }

    public static String formatInstantMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatInstantMach.format(date);
    }

    public static String formatDateFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatDateFile.format(date);
    }

    public static String formatTimeFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatTimeFile.format(date);
    }

    public static String formatMillisFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatMillisFile.format(date);
    }

    public static String formatDateTimeFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatDateTimeFile.format(date);
    }

    public static String formatTimestampFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatTimestampFile.format(date);
    }

    public static String formatInstantFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizUtilDate.formatInstantFile.format(date);
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
    public static SimpleDateFormat formatMillisUser = new SimpleDateFormat("HH:mm:ss ZZZ");
    public static SimpleDateFormat formatDateTimeUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static SimpleDateFormat formatTimestampUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ZZZ");
    public static SimpleDateFormat formatInstantUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ZZZ Z");

    public static SimpleDateFormat formatDateMach = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat formatTimeMach = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat formatMillisMach = new SimpleDateFormat("HH:mm:ss.ZZZ");
    public static SimpleDateFormat formatDateTimeMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat formatTimestampMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ZZZ");
    public static SimpleDateFormat formatInstantMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ZZZ Z");

    public static SimpleDateFormat formatDateFile = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat formatTimeFile = new SimpleDateFormat("HH-mm-ss");
    public static SimpleDateFormat formatMillisFile = new SimpleDateFormat("HH-mm-ss-ZZZ");
    public static SimpleDateFormat formatDateTimeFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    public static SimpleDateFormat formatTimestampFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ZZZ");
    public static SimpleDateFormat formatInstantFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ZZZ-Z");

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
