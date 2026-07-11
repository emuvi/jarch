package com.vidlus.jarch.mage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Objects;

import com.vidlus.jarch.data.Base;
import com.vidlus.jarch.data.DataClazz;
import com.vidlus.jarch.flow.FixVals;

/**
 * Utility class for common Object operations, null-safe validations,
 * base64 serialization, and file I/O for objects.
 */
public class WizObject {

    /**
     * Prefix used to indicate that a serialized string represents a DataClazz object.
     */
    public static final String dataClazzPrefix = "dataClazz:";

    /**
     * Prefix used to indicate that a serialized string represents a base64 encoded Serializable object.
     */
    public static final String objectOnBase64Prefix = "object^64:";

    private WizObject() {
    }

    // =========================================================================
    // VALIDATION & CORE UTILS
    // =========================================================================

    /**
     * Checks if the given object is null.
     *
     * @param object the object to check
     * @return true if the object is null, false otherwise
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Checks if the given object is not null.
     *
     * @param object the object to check
     * @return true if the object is not null, false otherwise
     */
    public static boolean isNotNull(Object object) {
        return object != null;
    }

    /**
     * Compares two objects for equality in a null-safe manner.
     *
     * @param a the first object
     * @param b the second object
     * @return true if the objects are equal, false otherwise
     */
    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    /**
     * Generates a hash code for the given object in a null-safe manner.
     *
     * @param object the object to hash
     * @return the hash code, or 0 if the object is null
     */
    public static int hashCode(Object object) {
        return Objects.hashCode(object);
    }

    /**
     * Converts an object to its string representation in a null-safe manner.
     *
     * @param object the object to convert
     * @return the string representation, or an empty string if the object is null
     */
    public static String toString(Object object) {
        return object == null ? "" : object.toString();
    }

    /**
     * Converts an object to its string representation, returning a default value if the object is null.
     *
     * @param object        the object to convert
     * @param defaultIfNull the default string to return if the object is null
     * @return the string representation, or defaultIfNull if the object is null
     */
    public static String toString(Object object, String defaultIfNull) {
        return object == null ? defaultIfNull : object.toString();
    }

    /**
     * Validates that the specified object reference is not null.
     *
     * @param <T>    the type of the reference
     * @param object the object reference to check for nullity
     * @return the object reference if not null
     * @throws NullPointerException if the object is null
     */
    public static <T> T requireNotNull(T object) {
        return Objects.requireNonNull(object);
    }

    /**
     * Validates that the specified object reference is not null and throws a customized exception message if it is.
     *
     * @param <T>     the type of the reference
     * @param object  the object reference to check for nullity
     * @param message the detail message to be used in the event that a NullPointerException is thrown
     * @return the object reference if not null
     * @throws NullPointerException if the object is null
     */
    public static <T> T requireNotNull(T object, String message) {
        return Objects.requireNonNull(object, message);
    }

    /**
     * Returns the first non-null object from a given array of objects.
     *
     * @param <T>        the type of the objects
     * @param objectList the array of objects to check
     * @return the first non-null object, or null if all elements are null or the array itself is null
     */
    @SafeVarargs
    public static <T> T getFirstNonNull(T... objectList) {
        if (objectList == null) {
            return null;
        }
        for (T object : objectList) {
            if (object != null) {
                return object;
            }
        }
        return null;
    }

    // =========================================================================
    // CONVERSION & SERIALIZATION
    // =========================================================================

    /**
     * Checks if a value is compatible with WizObject serialization or conversion routines.
     *
     * @param value the object to check
     * @return true if the object is a DataClazz, String, or a generic Object (meaning it passes as long as it's not null)
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), DataClazz.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Object.class);
    }

    /**
     * Extracts or decodes the actual value from a structured object or serialized string.
     * Handles DataClazz instances, base64 encoded byte arrays, and base64 serialized objects.
     *
     * @param value the object or formatted string to decode
     * @return the raw decoded object, or the original object if no special formatting was detected
     * @throws Exception if object deserialization or base64 decoding fails
     */
    public static Object get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), DataClazz.class)) {
            return DataClazz.class.cast(value).getValue();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isEmpty()) return null;
            if (string.startsWith(dataClazzPrefix)) {
                return DataClazz.fromChars(string.substring(dataClazzPrefix.length())).getValue();
            } else if (string.startsWith(WizBytes.byteArrayOnBase64Prefix)) {
                return WizBytes.decodeFromBase64(string.substring(WizBytes.byteArrayOnBase64Prefix.length()));
            } else if (string.startsWith(WizObject.objectOnBase64Prefix)) {
                var bytes = WizBytes.decodeFromBase64(string.substring(WizObject.objectOnBase64Prefix.length()));
                try (var bis = new ByteArrayInputStream(bytes);
                     var ois = new ObjectInputStream(bis)) {
                    return ois.readObject();
                }
            } else {
                return string;
            }
        }
        return value;
    }

    /**
     * Formats an object into a portable string representation.
     * If the object is a DataClazz, FixVals, Base, or Record, it is formatted with a specific prefix.
     * If it is a byte array, it is encoded to base64.
     * Otherwise, it serializes the entire object into a base64 encoded string.
     *
     * @param value the object to serialize or format
     * @return the formatted or base64 encoded string
     * @throws RuntimeException if the object is not Serializable or serialization fails
     */
    public static String format(Object value) {
        if (value == null) return "";
        if (WizLang.isChildOf(value.getClass(), DataClazz.class)) {
            return dataClazzPrefix + DataClazz.class.cast(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), FixVals.class)) {
            return dataClazzPrefix + new DataClazz(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), Base.class)) {
            return dataClazzPrefix + new DataClazz(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), Record.class)) {
            return dataClazzPrefix + new DataClazz(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            return WizBytes.byteArrayOnBase64Prefix + WizBytes.encodeToBase64(byte[].class.cast(value));
        }
        try (var bos = new ByteArrayOutputStream();
             var oos = new ObjectOutputStream(bos)) {
            oos.writeObject(value);
            oos.flush();
            return objectOnBase64Prefix + WizBytes.encodeToBase64(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object of class: " + value.getClass().getName(), e);
        }
    }
    
    // =========================================================================
    // FILE I/O
    // =========================================================================

    /**
     * Reads a serialized object from a file and casts it to the target class.
     *
     * @param <T>   the expected type of the object
     * @param file  the file containing the serialized object
     * @param clazz the Class object corresponding to the expected type
     * @return the deserialized object cast to the given class
     * @throws Exception if an I/O error occurs, class is not found, or casting fails
     */
    public static <T> T read(File file, Class<T> clazz) throws Exception {
        return clazz.cast(read(file));
    }

    /**
     * Reads a serialized object from a file.
     *
     * @param file the file containing the serialized object
     * @return the deserialized object
     * @throws Exception if an I/O error occurs or the object's class cannot be found
     */
    public static Object read(File file) throws Exception {
        try (var ois = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            return ois.readObject();
        }
    }

    /**
     * Writes a Serializable object to a file.
     *
     * @param file   the target file to write to
     * @param object the Serializable object to save
     * @throws Exception if an I/O error occurs during writing
     */
    public static void write(File file, Serializable object) throws Exception {
        try (var oos = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            oos.writeObject(object);
            oos.flush();
        }
    }

}
