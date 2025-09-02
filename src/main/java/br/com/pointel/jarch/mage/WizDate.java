package br.com.pointel.jarch.mage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class WizDate {

    public static Date get(Object value) throws Exception {
        if (value == null) {
            return null;
        }
        if (Date.class.isAssignableFrom(value.getClass())) {
            return (Date) value;
        }
        if (value instanceof java.sql.Date date) {
            return new Date(date.getTime());
        } else if (value instanceof java.sql.Time time) {
            return new Date(time.getTime());
        } else if (value instanceof java.sql.Timestamp timestamp) {
            return new Date(timestamp.getTime());
        } else if (value instanceof String string) {
            for (var format : WizDate.getFormats()) {
                if (WizDate.is(string, format)) {
                    return format.parse(string);
                }
            }
        }
        throw new Exception("Could not convert the value of class " + value.getClass().getName() + " to a Date value.");
    }

    public static boolean is(String formatted, SimpleDateFormat inFormat) {
        return Objects.equals(WizChars.replaceLettersOrDigits(formatted, 'x'), WizChars.replaceLettersOrDigits(inFormat.toPattern(), 'x'));
    }

    public static String formatDateUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatDateUser.format(date);
    }

    public static String formatTimeUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatTimeUser.format(date);
    }

    public static String formatMillisUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatMillisUser.format(date);
    }

    public static String formatDateTimeUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatDateTimeUser.format(date);
    }

    public static String formatTimestampUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatTimestampUser.format(date);
    }

    public static String formatMomentUser(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatMomentUser.format(date);
    }

    public static String formatDateMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatDateMach.format(date);
    }

    public static String formatTimeMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatTimeMach.format(date);
    }

    public static String formatMillisMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatMillisMach.format(date);
    }

    public static String formatDateTimeMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatDateTimeMach.format(date);
    }

    public static String formatTimestampMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatTimestampMach.format(date);
    }

    public static String formatMomentMach(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatMomentMach.format(date);
    }

    public static String formatDateFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatDateFile.format(date);
    }

    public static String formatTimeFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatTimeFile.format(date);
    }

    public static String formatMillisFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatMillisFile.format(date);
    }

    public static String formatDateTimeFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatDateTimeFile.format(date);
    }

    public static String formatTimestampFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatTimestampFile.format(date);
    }

    public static String formatMomentFile(Date date) {
        if (date == null) {
            return "";
        }
        return WizDate.formatMomentFile.format(date);
    }

    public static Date parseDateUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatDateUser.parse(formatted);
    }

    public static Date parseTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatTimeUser.parse(formatted);
    }

    public static Date parseMillisUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatMillisUser.parse(formatted);
    }

    public static Date parseDateTimeUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatDateTimeUser.parse(formatted);
    }

    public static Date parseTimestampUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatTimestampUser.parse(formatted);
    }

    public static Date parseMomentUser(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatMomentUser.parse(formatted);
    }

    public static Date parseDateMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatDateMach.parse(formatted);
    }

    public static Date parseTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatTimeMach.parse(formatted);
    }

    public static Date parseMillisMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatMillisMach.parse(formatted);
    }

    public static Date parseDateTimeMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatDateTimeMach.parse(formatted);
    }

    public static Date parseTimestampMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatTimestampMach.parse(formatted);
    }

    public static Date parseMomentMach(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatMomentMach.parse(formatted);
    }

    public static Date parseDateFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatDateFile.parse(formatted);
    }

    public static Date parseTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatTimeFile.parse(formatted);
    }

    public static Date parseTimeMillisFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatMillisFile.parse(formatted);
    }

    public static Date parseDateTimeFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatDateTimeFile.parse(formatted);
    }

    public static Date parseTimestampFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatTimestampFile.parse(formatted);
    }

    public static Date parseMomentFile(String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        return WizDate.formatMomentFile.parse(formatted);
    }

    public static SimpleDateFormat formatDateUser = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat formatTimeUser = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat formatMillisUser = new SimpleDateFormat("HH:mm:ss ZZZ");
    public static SimpleDateFormat formatDateTimeUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static SimpleDateFormat formatTimestampUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z");
    public static SimpleDateFormat formatMomentUser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ZZZ Z");

    public static SimpleDateFormat formatDateMach = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat formatTimeMach = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat formatMillisMach = new SimpleDateFormat("HH:mm:ss.ZZZ");
    public static SimpleDateFormat formatDateTimeMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat formatTimestampMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    public static SimpleDateFormat formatMomentMach = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ZZZ Z");

    public static SimpleDateFormat formatDateFile = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat formatTimeFile = new SimpleDateFormat("HH-mm-ss");
    public static SimpleDateFormat formatMillisFile = new SimpleDateFormat("HH-mm-ss-ZZZ");
    public static SimpleDateFormat formatDateTimeFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    public static SimpleDateFormat formatTimestampFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-Z");
    public static SimpleDateFormat formatMomentFile = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ZZZ-Z");

    public static SimpleDateFormat[] getFormats() {
        return new SimpleDateFormat[] {
                WizDate.formatDateMach,
                WizDate.formatTimeMach,
                WizDate.formatMillisMach,
                WizDate.formatDateTimeMach,
                WizDate.formatTimestampMach,
                WizDate.formatMomentMach,
                WizDate.formatDateUser,
                WizDate.formatTimeUser,
                WizDate.formatMillisUser,
                WizDate.formatDateTimeUser,
                WizDate.formatTimestampUser,
                WizDate.formatMomentUser,
                WizDate.formatDateFile,
                WizDate.formatTimeFile,
                WizDate.formatMillisFile,
                WizDate.formatDateTimeFile,
                WizDate.formatTimestampFile,
                WizDate.formatMomentFile
        };
    };
}
