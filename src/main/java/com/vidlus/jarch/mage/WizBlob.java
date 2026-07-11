package com.vidlus.jarch.mage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;

import javax.sql.rowset.serial.SerialBlob;

public class WizBlob {

    private WizBlob() {
    }

    /**
     * Checks if the given value can be converted to a Blob.
     * Supports Blob, byte[], Clob, String, Number, and Serializable types.
     *
     * @param value the value to check
     * @return true if value can be converted to Blob; false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Blob.class)
            || WizLang.isChildOf(value.getClass(), byte[].class)
            || WizLang.isChildOf(value.getClass(), Clob.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Serializable.class);
    }

    /**
     * Converts the given value to a Blob.
     * Handles Blob, byte[], Clob, String, Number, and Serializable types.
     * Serializable objects are serialized using ObjectOutputStream.
     * Text values are encoded using UTF-8.
     *
     * @param value the value to convert
     * @return a Blob representing the value, or null if value is null
     * @throws Exception if the value cannot be converted to Blob or serialization fails
     */
    public static Blob get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Blob.class)) {
            return Blob.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            return new SerialBlob(byte[].class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), Clob.class)) {
            var clob = Clob.class.cast(value);
            return new SerialBlob(clob.getSubString(1, (int) clob.length()).getBytes(StandardCharsets.UTF_8));
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            return new SerialBlob(string.getBytes(StandardCharsets.UTF_8));
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            var number = Number.class.cast(value);
            return new SerialBlob(String.valueOf(number).getBytes(StandardCharsets.UTF_8));
        }
        if (WizLang.isChildOf(value.getClass(), Serializable.class)) {
            try (var bos = new ByteArrayOutputStream();
                var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(value);
                oos.flush();
                return new SerialBlob(bos.toByteArray());
            }
        }
        throw new Exception("Could not convert to a Blob value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts the given value to a Blob, returning a default if conversion fails.
     * 
     * @param value the value to convert
     * @param orDefault the default Blob to return if conversion fails
     * @return the converted Blob, or orDefault if conversion fails or value is null
     */
    public static Blob get(Object value, Blob orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Extracts the raw byte array from a Blob.
     *
     * @param blob the Blob to extract bytes from
     * @return a byte array, or null if blob is null
     * @throws Exception if accessing the Blob fails
     */
    public static byte[] getBytes(Blob blob) throws Exception {
        if (blob == null) return null;
        return blob.getBytes(1, (int) blob.length());
    }

    /**
     * Converts a Blob to a String using UTF-8 charset.
     *
     * @param blob the Blob to convert
     * @return the string representation, or null if blob is null
     * @throws Exception if accessing the Blob fails
     */
    public static String getString(Blob blob) throws Exception {
        return getString(blob, StandardCharsets.UTF_8);
    }

    /**
     * Converts a Blob to a String using the specified charset.
     *
     * @param blob the Blob to convert
     * @param charset the character set to use for decoding
     * @return the string representation, or null if blob is null
     * @throws Exception if accessing the Blob fails
     */
    public static String getString(Blob blob, Charset charset) throws Exception {
        byte[] bytes = getBytes(blob);
        if (bytes == null) return null;
        return new String(bytes, charset);
    }

    /**
     * Deserializes a Blob back to its original Object.
     * Used for Blobs created from Serializable objects.
     *
     * @param blob the Blob to deserialize
     * @return the deserialized object, or null if blob is null
     * @throws Exception if accessing the Blob or deserialization fails
     */
    public static Object getObject(Blob blob) throws Exception {
        byte[] bytes = getBytes(blob);
        if (bytes == null) return null;
        try (var bis = new ByteArrayInputStream(bytes);
             var ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        }
    }

    /**
     * Gets an InputStream for reading the Blob content.
     * The caller is responsible for closing the stream.
     *
     * @param blob the Blob to read from
     * @return an InputStream, or null if blob is null
     * @throws Exception if accessing the Blob fails
     */
    public static InputStream getInputStream(Blob blob) throws Exception {
        if (blob == null) return null;
        return blob.getBinaryStream();
    }

    /**
     * Returns the length of a Blob in bytes.
     * Handles exceptions gracefully and returns 0 on error.
     *
     * @param blob the Blob to measure
     * @return the length in bytes, or 0 if blob is null or an error occurs
     */
    public static long length(Blob blob) {
        if (blob == null) return 0;
        try {
            return blob.length();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Frees the resources associated with a Blob.
     * Suppresses any exceptions that occur during cleanup.
     *
     * @param blob the Blob to free (can be null)
     */
    public static void free(Blob blob) {
        if (blob != null) {
            try {
                blob.free();
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    /**
     * Checks if a Blob is empty (has zero bytes).
     *
     * @param blob the Blob to check
     * @return true if blob is null or has no bytes; false otherwise
     */
    public static boolean isEmpty(Blob blob) {
        return length(blob) == 0;
    }

    /**
     * Checks if a Blob is not empty (has at least one byte).
     *
     * @param blob the Blob to check
     * @return true if blob has at least one byte; false otherwise
     */
    public static boolean isNotEmpty(Blob blob) {
        return !isEmpty(blob);
    }

}
