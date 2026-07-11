package com.vidlus.jarch.mage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
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
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vidlus.jarch.data.Nature;

public class WizData {

    private static final Gson gson = new Gson();
    private static final Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

    private WizData() {
    }

    /**
     * Serializes an object and writes it to a file.
     */
    public static void toFile(Serializable value, File file) throws Exception {
        try (var fileOut = new FileOutputStream(file);
             var objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(value);
        }
    }

    /**
     * Reads a serialized object from a file.
     */
    public static <T> T fromFile(Class<T> clazz, File file) throws Exception {
        try (var fileIn = new FileInputStream(file);
             var objectIn = new ObjectInputStream(fileIn)) {
            return clazz.cast(objectIn.readObject());
        }
    }

    /**
     * Serializes an object to a byte array.
     */
    public static byte[] toBytes(Serializable value) throws Exception {
        if (value == null) return null;
        try (var bos = new ByteArrayOutputStream();
             var oos = new ObjectOutputStream(bos)) {
            oos.writeObject(value);
            oos.flush();
            return bos.toByteArray();
        }
    }

    /**
     * Deserializes an object from a byte array.
     */
    public static <T> T fromBytes(byte[] bytes, Class<T> clazz) throws Exception {
        if (bytes == null || bytes.length == 0) return null;
        try (var bis = new ByteArrayInputStream(bytes);
             var ois = new ObjectInputStream(bis)) {
            return clazz.cast(ois.readObject());
        }
    }

    /**
     * Serializes an object to a Base64 encoded string.
     */
    public static String toBase64(Serializable value) throws Exception {
        if (value == null) return null;
        byte[] bytes = toBytes(value);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Deserializes an object from a Base64 encoded string.
     */
    public static <T> T fromBase64(String base64, Class<T> clazz) throws Exception {
        if (base64 == null || base64.isEmpty()) return null;
        byte[] bytes = Base64.getDecoder().decode(base64);
        return fromBytes(bytes, clazz);
    }

    /**
     * Performs a deep clone of a Serializable object via serialization/deserialization.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T value) {
        if (value == null) return null;
        try {
            byte[] bytes = toBytes(value);
            return (T) fromBytes(bytes, value.getClass());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Converts an object to a JSON string.
     */
    public static String toJson(Object value) {
        return gson.toJson(value);
    }

    /**
     * Converts an object to a pretty-printed JSON string.
     */
    public static String toJsonPretty(Object value) {
        return gsonPretty.toJson(value);
    }

    /**
     * Parses a JSON string into an object of the specified class.
     */
    public static <T> T fromJson(String chars, Class<T> ofClass) {
        return gson.fromJson(chars, ofClass);
    }

    /**
     * Parses a JSON array string into a List of the specified class.
     */
    public static <T> List<T> fromJsonList(String chars, Class<T> ofClass) {
        Type type = TypeToken.getParameterized(List.class, ofClass).getType();
        return gson.fromJson(chars, type);
    }

    /**
     * Converts a value to a formatted String according to its Nature data type.
     * Uses appropriate Wiz* formatter classes based on the Nature enum.
     *
     * @param nature the Nature enum defining the data type
     * @param value the value to format
     * @return the formatted string representation
     * @throws Exception if the Nature type is not supported or formatting fails
     */
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

    /**
     * Parses a formatted String into an object of the specified type based on its Nature.
     *
     * @param nature the Nature enum defining the data type
     * @param formatted the formatted string to parse
     * @param onClass the target class to convert to
     * @param <T> the type parameter
     * @return the parsed object cast to the target class
     * @throws Exception if parsing or conversion fails
     */
    public static <T> T fromFormatted(Nature nature, String formatted, Class<T> onClass) throws Exception {
        return getOn(fromFormatted(nature, formatted), onClass);
    }

    /**
     * Parses a formatted String into an object based on its Nature data type.
     *
     * @param nature the Nature enum defining the data type
     * @param formatted the formatted string to parse
     * @return the parsed object, or null if formatted is null or empty
     * @throws Exception if the Nature type is not supported or parsing fails
     */
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

    /**
     * Converts a value to the specified target class using appropriate Wiz* converters.
     * Supports all primitive wrapper types, temporal types, binary types, and more.
     *
     * @param value the value to convert
     * @param onClass the target class to convert to
     * @param <T> the type parameter
     * @return the value converted to the target class
     * @throws UnsupportedOperationException if the target class is not supported
     */
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
        throw new UnsupportedOperationException("Could not convert to " + onClass.getName() + " from class: " + value.getClass().getName());
    }

    /**
     * Converts a value safely, returning a default if conversion fails.
     */
    public static <T> T getOn(Object value, Class<T> onClass, T orDefault) {
        try {
            T result = getOn(value, onClass);
            return result != null ? result : orDefault;
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Discovers the Nature enum associated with a given Class.
     * Helpful for ORM mappers or dynamic SQL building.
     */
    public static Nature getNatureOf(Class<?> clazz) {
        if (clazz == null) return Nature.Object;
        
        if (WizLang.isChildOf(clazz, Boolean.class)) return Nature.Bool;
        if (WizLang.isChildOf(clazz, Byte.class)) return Nature.Byte;
        if (WizLang.isChildOf(clazz, Short.class)) return Nature.Small;
        if (WizLang.isChildOf(clazz, Integer.class)) return Nature.Int;
        if (WizLang.isChildOf(clazz, Long.class)) return Nature.Long;
        if (WizLang.isChildOf(clazz, Float.class)) return Nature.Float;
        if (WizLang.isChildOf(clazz, Double.class)) return Nature.Double;
        if (WizLang.isChildOf(clazz, BigInteger.class)) return Nature.BigInt;
        if (WizLang.isChildOf(clazz, BigDecimal.class)) return Nature.Numeric;
        if (WizLang.isChildOf(clazz, Character.class)) return Nature.Char;
        if (WizLang.isChildOf(clazz, String.class)) return Nature.Chars;
        if (WizLang.isChildOf(clazz, Instant.class)) return Nature.Timestamp;
        if (WizLang.isChildOf(clazz, ZonedDateTime.class)) return Nature.ZoneTime;
        if (WizLang.isChildOf(clazz, LocalDateTime.class)) return Nature.DateTime;
        if (WizLang.isChildOf(clazz, LocalDate.class)) return Nature.Date;
        if (WizLang.isChildOf(clazz, LocalTime.class)) return Nature.Time;
        if (WizLang.isChildOf(clazz, java.sql.Timestamp.class)) return Nature.Timestamp;
        if (WizLang.isChildOf(clazz, java.sql.Date.class)) return Nature.Date;
        if (WizLang.isChildOf(clazz, java.sql.Time.class)) return Nature.Time;
        if (WizLang.isChildOf(clazz, java.util.Date.class)) return Nature.Timestamp;
        if (WizLang.isChildOf(clazz, byte[].class)) return Nature.Bytes;
        if (WizLang.isChildOf(clazz, Blob.class)) return Nature.Blob;
        if (WizLang.isChildOf(clazz, Clob.class)) return Nature.Text;
        
        return Nature.Object;
    }

    /**
     * Checks if a given class is a supported Nature data type.
     * Returns true for primitive wrappers, temporal types, binary types, and more.
     *
     * @param clazz the class to check
     * @return true if the class is a supported Nature data type; false otherwise
     * @throws Exception if an error occurs during checking
     */
    /**
     * Checks if a given class is a supported Nature data type.
     * Returns true for primitive wrappers, temporal types, binary types, and more.
     *
     * @param clazz the class to check
     * @return true if the class is a supported Nature data type; false otherwise
     * @throws Exception if an error occurs during checking
     */
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
