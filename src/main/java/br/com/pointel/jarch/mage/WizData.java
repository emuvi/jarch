package br.com.pointel.jarch.mage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import com.google.gson.Gson;
import br.com.pointel.jarch.data.Nature;

public class WizData {

    private static final Gson gson = new Gson();

    private WizData() {
    }

    public static String toChars(Object value) {
        return gson.toJson(value);
    }

    public static <T> T fromChars(String chars, Class<T> clazz) {
        return gson.fromJson(chars, clazz);
    }

    public static String toFormatted(Nature nature, Object value) throws Exception {
        if (value == null) return "";
        switch (nature) {
            case Bool: return WizBoolean.format(WizBoolean.get(value));
            case Bit: return WizByte.format(WizByte.get(value));
            case Byte: return WizByte.format(WizByte.get(value));
            case Tiny: return WizShort.format(WizShort.get(value));
            case Small: return WizShort.format(WizShort.get(value));
            case Int: return WizInteger.format(WizInteger.get(value));
            case Long: return WizLong.format(WizLong.get(value));
            case BigInt: return WizBigInteger.format(WizBigInteger.get(value));
            case Serial: return WizInteger.format(WizInteger.get(value));
            case BigSerial: return WizBigInteger.format(WizBigInteger.get(value));
            case Float: return WizFloat.format(WizFloat.get(value));
            case Real: return WizDouble.format(WizDouble.get(value));
            case Double: return WizDouble.format(WizDouble.get(value));
            case Numeric: return WizBigDecimal.format(WizBigDecimal.get(value));
            case BigNumeric: return WizBigDecimal.format(WizBigDecimal.get(value));
            case Char: return WizCharacter.format(WizCharacter.get(value));
            case Chars: return WizString.get(value);
            case Date: return WizLocalDate.format(WizLocalDate.get(value));
            case Time: return WizLocalTime.format(WizLocalTime.get(value));
            case DateTime: return WizLocalDateTime.format(WizLocalDateTime.get(value));
            case ZoneTime: return WizZonedDateTime.format(WizZonedDateTime.get(value));
            case Timestamp: return WizInstant.format(WizInstant.get(value));
            case Bytes: return WizBytes.format(WizBytes.get(value));
            case Blob: return WizBytes.format(WizBytes.get(value));
            case Text: return WizString.get(value);
            case Object: return WizObject.format(value);
        }
        throw new Exception("Nature " + nature + " not supported for value class: " + value.getClass().getName());
    }

    public static <T> T fromFormatted(Nature nature, String formatted, Class<T> onClass) throws Exception {
        return getOn(fromFormatted(nature, formatted), onClass);
    }

    public static Object fromFormatted(Nature nature, String formatted) throws Exception {
        if (formatted == null || formatted.isEmpty()) {
            return null;
        }
        switch (nature) {
            case Bool: return WizBoolean.get(formatted);
            case Bit: return WizByte.get(formatted);
            case Byte: return WizByte.get(formatted);
            case Tiny: return WizShort.get(formatted);
            case Small: return WizShort.get(formatted);
            case Int: return WizInteger.get(formatted);
            case Long: return WizLong.get(formatted);
            case BigInt: return WizBigInteger.get(formatted);
            case Serial: return WizInteger.get(formatted);
            case BigSerial: return WizBigInteger.get(formatted);
            case Float: return WizFloat.get(formatted);
            case Real: return WizDouble.get(formatted);
            case Double: return WizDouble.get(formatted);
            case Numeric: return WizBigDecimal.get(formatted);
            case BigNumeric: return WizBigDecimal.get(formatted);
            case Char: return WizCharacter.get(formatted);
            case Chars: return WizString.get(formatted);
            case Date: return WizLocalDate.get(formatted);
            case Time: return WizLocalTime.get(formatted);
            case DateTime: return WizLocalDateTime.get(formatted);
            case ZoneTime: return WizZonedDateTime.get(formatted);
            case Timestamp: return WizInstant.get(formatted);
            case Bytes: return WizBytes.get(formatted);
            case Blob: return WizBytes.get(formatted);
            case Text: return WizString.get(formatted);
            case Object: return WizObject.get(formatted);
        }
        throw new Exception("Nature " + nature + " not supported for the formatted: " + formatted);
    }

    public static <T> T getOn(Object value, Class<T> onClass) throws Exception {
        if (value == null) {
            return null;
        }
        if (WizLang.isChildOf(onClass, Boolean.class)) {
            return onClass.cast(WizBoolean.get(value));
        }
        if (WizLang.isChildOf(onClass, Byte.class)) {
            return onClass.cast(WizByte.get(value));
        }
        if (WizLang.isChildOf(onClass, Short.class)) {
            return onClass.cast(WizShort.get(value));
        }
        if (WizLang.isChildOf(onClass, Integer.class)) {
            return onClass.cast(WizInteger.get(value));
        }
        if (WizLang.isChildOf(onClass, Long.class)) {
            return onClass.cast(WizLong.get(value));
        }
        if (WizLang.isChildOf(onClass, Float.class)) {
            return onClass.cast(WizFloat.get(value));
        }
        if (WizLang.isChildOf(onClass, Double.class)) {
            return onClass.cast(WizDouble.get(value));
        }
        if (WizLang.isChildOf(onClass, BigInteger.class)) {
            return onClass.cast(WizBigInteger.get(value));
        }
        if (WizLang.isChildOf(onClass, BigDecimal.class)) {
            return onClass.cast(WizBigDecimal.get(value));
        }
        if (WizLang.isChildOf(onClass, Character.class)) {
            return onClass.cast(WizCharacter.get(value));
        }
        if (WizLang.isChildOf(onClass, String.class)) {
            return onClass.cast(WizString.get(value));
        }
        if (WizLang.isChildOf(onClass, Instant.class)) {
            return onClass.cast(WizInstant.get(value));
        }
        if (WizLang.isChildOf(onClass, ZonedDateTime.class)) {
            return onClass.cast(WizZonedDateTime.get(value));
        }
        if (WizLang.isChildOf(onClass, OffsetDateTime.class)) {
            return onClass.cast(WizOffsetDateTime.get(value));
        }
        if (WizLang.isChildOf(onClass, OffsetTime.class)) {
            return onClass.cast(WizOffsetTime.get(value));
        }
        if (WizLang.isChildOf(onClass, LocalDateTime.class)) {
            return onClass.cast(WizLocalDateTime.get(value));
        }
        if (WizLang.isChildOf(onClass, LocalDate.class)) {
            return onClass.cast(WizLocalDate.get(value));
        }
        if (WizLang.isChildOf(onClass, LocalTime.class)) {
            return onClass.cast(WizLocalTime.get(value));
        }
        if (WizLang.isChildOf(onClass, java.util.Date.class)) {
            return onClass.cast(WizUtilDate.get(value));
        }
        if (WizLang.isChildOf(onClass, java.sql.Date.class)) {
            return onClass.cast(WizSqlDate.get(value));
        }
        if (WizLang.isChildOf(onClass, java.sql.Time.class)) {
            return onClass.cast(WizSqlTime.get(value));
        }
        if (WizLang.isChildOf(onClass, java.sql.Timestamp.class)) {
            return onClass.cast(WizSqlTimestamp.get(value));
        }
        if (WizLang.isChildOf(onClass, byte[].class)) {
            return onClass.cast(WizBytes.get(value));
        }
        if (WizLang.isChildOf(onClass, Blob.class)) {
            return onClass.cast(WizBlob.get(value));
        }
        if (WizLang.isChildOf(onClass, Clob.class)) {
            return onClass.cast(WizClob.get(value));
        }
        throw new UnsupportedOperationException("Could not convert to " + onClass.getCanonicalName() + " from class: " + value.getClass().getName());
    }

    public static boolean isNatureData(Class<?> clazz) throws Exception {
        if (clazz == null) {
            return false;
        }
        return WizLang.isChildOf(clazz, Boolean.class)
            || WizLang.isChildOf(clazz, Byte.class)
            || WizLang.isChildOf(clazz, Short.class)
            || WizLang.isChildOf(clazz, Integer.class)
            || WizLang.isChildOf(clazz, Long.class)
            || WizLang.isChildOf(clazz, Float.class)
            || WizLang.isChildOf(clazz, Double.class)
            || WizLang.isChildOf(clazz, BigInteger.class)
            || WizLang.isChildOf(clazz, BigDecimal.class)
            || WizLang.isChildOf(clazz, Character.class)
            || WizLang.isChildOf(clazz, String.class)
            || WizLang.isChildOf(clazz, Instant.class)
            || WizLang.isChildOf(clazz, ZonedDateTime.class)
            || WizLang.isChildOf(clazz, OffsetDateTime.class)
            || WizLang.isChildOf(clazz, OffsetTime.class)
            || WizLang.isChildOf(clazz, LocalDateTime.class)
            || WizLang.isChildOf(clazz, LocalDate.class)
            || WizLang.isChildOf(clazz, LocalTime.class)
            || WizLang.isChildOf(clazz, java.util.Date.class)
            || WizLang.isChildOf(clazz, java.sql.Date.class)
            || WizLang.isChildOf(clazz, java.sql.Time.class)
            || WizLang.isChildOf(clazz, java.sql.Timestamp.class)
            || WizLang.isChildOf(clazz, byte[].class)
            || WizLang.isChildOf(clazz, Blob.class)
            || WizLang.isChildOf(clazz, Clob.class);
    }

}
