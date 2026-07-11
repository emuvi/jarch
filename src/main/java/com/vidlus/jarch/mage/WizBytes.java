package com.vidlus.jarch.mage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class WizBytes {

    /**
     * Prefix used for Base64-encoded byte array strings.
     * Default value: "byte[]^64:"
     */
    public static String byteArrayOnBase64Prefix = "byte[]^64:";

    private WizBytes() {
    }

    /**
     * Checks if the given value can be converted to a byte array.
     * Supports byte[], Blob, Clob, String, Number, and Serializable types.
     *
     * @param value the value to check
     * @return true if value can be converted to byte[]; false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), byte[].class)
            || WizLang.isChildOf(value.getClass(), Blob.class)
            || WizLang.isChildOf(value.getClass(), Clob.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Serializable.class);
    }

    /**
     * Converts the given value to a byte array.
     * Handles byte[], Blob, Clob, String (with Base64 decoding support), Number, and Serializable types.
     * Empty strings return null.
     *
     * @param value the value to convert
     * @return a byte array, or null if value is null or empty string
     * @throws Exception if the value cannot be converted to byte[]
     */
    public static byte[] get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            return byte[].class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Blob.class)) {
            var blob = Blob.class.cast(value);
            return blob.getBytes(1, (int) blob.length());
        }
        if (WizLang.isChildOf(value.getClass(), Clob.class)) {
            var clob = Clob.class.cast(value);
            return clob.getSubString(1, (int) clob.length()).getBytes(StandardCharsets.UTF_8);
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isEmpty()) return null;
            if (string.startsWith(byteArrayOnBase64Prefix)) {
                return WizBytes.decodeFromBase64(string.substring(byteArrayOnBase64Prefix.length()));
            } else if (string.startsWith(WizObject.objectOnBase64Prefix)) {
                return WizBytes.decodeFromBase64(string.substring(WizObject.objectOnBase64Prefix.length()));
            } else {
                return string.getBytes(StandardCharsets.UTF_8);
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return String.valueOf(Number.class.cast(value)).getBytes(StandardCharsets.UTF_8);
        }
        if (WizLang.isChildOf(value.getClass(), Serializable.class)) {
            try (var bos = new ByteArrayOutputStream();
                    var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(Serializable.class.cast(value));
                oos.flush();
                return bos.toByteArray();
            }
        }
        throw new Exception("Could not convert to a byte[] value the value of class: " + value.getClass().getName());
    }

    /**
     * Formats a byte array as a Base64-encoded string with a prefix.
     *
     * @param value the byte array to format
     * @return the Base64-encoded string with byteArrayOnBase64Prefix, or empty string if value is null
     */
    public static String format(byte[] value) {
        if (value == null) return "";
        return byteArrayOnBase64Prefix + WizBytes.encodeToBase64(value);
    }

    /**
     * Encodes a byte array to a Base64 string.
     *
     * @param bytes the byte array to encode
     * @return the Base64-encoded string
     */
    public static String encodeToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Decodes a Base64 string to a byte array.
     *
     * @param formatted the Base64-encoded string to decode
     * @return the decoded byte array
     * @throws IllegalArgumentException if the string is not valid Base64
     */
    public static byte[] decodeFromBase64(String formatted) {
        return Base64.getDecoder().decode(formatted);
    }

    /**
     * Encodes a byte array to a hexadecimal string.
     * Each byte is represented by two hex digits (0-9, a-f).
     *
     * @param bytes the byte array to encode
     * @return the hexadecimal string representation
     */
    public static String encodeToHex(byte[] bytes) {
        var hexString = new StringBuilder(2 * bytes.length);
        for (byte element : bytes) {
            var hex = Integer.toHexString(0xff & element);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Computes the MD5 hash of a file.
     *
     * @param file the file to hash
     * @return the MD5 hash as a hexadecimal string
     * @throws Exception if file I/O or hashing fails
     */
    public static String getMD5(File file) throws Exception {
        return WizBytes.getMD5(Files.readAllBytes(file.toPath()));
    }

    /**
     * Computes the MD5 hash of a byte array.
     *
     * @param bytes the byte array to hash
     * @return the MD5 hash as a hexadecimal string
     * @throws Exception if hashing fails
     */
    public static String getMD5(byte[] bytes) throws Exception {
        return WizBytes.encodeToHex(MessageDigest.getInstance("MD5").digest(bytes));
    }

    /**
     * Computes the SHA-1 hash of a file.
     *
     * @param file the file to hash
     * @return the SHA-1 hash as a hexadecimal string
     * @throws Exception if file I/O or hashing fails
     */
    public static String getSHA1(File file) throws Exception {
        return WizBytes.getSHA1(Files.readAllBytes(file.toPath()));
    }

    /**
     * Computes the SHA-1 hash of a byte array.
     *
     * @param bytes the byte array to hash
     * @return the SHA-1 hash as a hexadecimal string
     * @throws Exception if hashing fails
     */
    public static String getSHA1(byte[] bytes) throws Exception {
        return WizBytes.encodeToHex(MessageDigest.getInstance("SHA-1").digest(bytes));
    }

    /**
     * Computes the SHA-256 hash of a file.
     *
     * @param file the file to hash
     * @return the SHA-256 hash as a hexadecimal string
     * @throws Exception if file I/O or hashing fails
     */
    public static String getSHA256(File file) throws Exception {
        return WizBytes.getSHA256(Files.readAllBytes(file.toPath()));
    }

    /**
     * Computes the SHA-256 hash of a byte array.
     *
     * @param bytes the byte array to hash
     * @return the SHA-256 hash as a hexadecimal string
     * @throws Exception if hashing fails
     */
    public static String getSHA256(byte[] bytes) throws Exception {
        return WizBytes.encodeToHex(MessageDigest.getInstance("SHA-256").digest(bytes));
    }

    /**
     * Converts the given value to a byte array, returning a default if conversion fails.
     *
     * @param value the value to convert
     * @param orDefault the default byte array to return if conversion fails
     * @return the converted byte array, or orDefault if conversion fails or value is null
     */
    public static byte[] get(Object value, byte[] orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Decodes a hexadecimal string to a byte array.
     * The hex string must have an even length with valid hex characters (0-9, a-f, A-F).
     *
     * @param hexString the hexadecimal string to decode
     * @return the decoded byte array
     * @throws IllegalArgumentException if hex string has odd length or contains invalid characters
     */
    public static byte[] decodeFromHex(String hexString) {
        if (hexString == null) return null;
        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex string must have an even length");
        }
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            int high = Character.digit(hexString.charAt(i), 16);
            int low = Character.digit(hexString.charAt(i + 1), 16);
            if (high == -1 || low == -1) {
                throw new IllegalArgumentException("Invalid hex character in string");
            }
            bytes[i / 2] = (byte) ((high << 4) + low);
        }
        return bytes;
    }

    /**
     * Concatenates two byte arrays.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return a new byte array with a followed by b, or null if both are null
     */
    public static byte[] concat(byte[] a, byte[] b) {
        if (a == null) return b != null ? b.clone() : null;
        if (b == null) return a.clone();
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * Extracts a subarray from a byte array.
     *
     * @param bytes the byte array to extract from
     * @param start the starting index (inclusive)
     * @param length the number of bytes to extract
     * @return a new byte array with the extracted bytes, or null if bytes is null
     */
    public static byte[] subBytes(byte[] bytes, int start, int length) {
        if (bytes == null) return null;
        if (start < 0) start = 0;
        if (length < 0) length = 0;
        if (start + length > bytes.length) length = bytes.length - start;
        if (length <= 0) return new byte[0];
        byte[] result = new byte[length];
        System.arraycopy(bytes, start, result, 0, length);
        return result;
    }

    /**
     * Checks if a byte array is empty (null or zero length).
     *
     * @param bytes the byte array to check
     * @return true if bytes is null or empty; false otherwise
     */
    public static boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    /**
     * Checks if a byte array is not empty.
     *
     * @param bytes the byte array to check
     * @return true if bytes is not null and has at least one element; false otherwise
     */
    public static boolean isNotEmpty(byte[] bytes) {
        return !isEmpty(bytes);
    }

    /**
     * Finds the first index of a byte in the array.
     *
     * @param bytes the byte array to search
     * @param b the byte to search for
     * @return the index of the first occurrence, or -1 if not found
     */
    public static int indexOf(byte[] bytes, byte b) {
        if (bytes == null) return -1;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == b) return i;
        }
        return -1;
    }

    /**
     * Finds the last index of a byte in the array.
     *
     * @param bytes the byte array to search
     * @param b the byte to search for
     * @return the index of the last occurrence, or -1 if not found
     */
    public static int lastIndexOf(byte[] bytes, byte b) {
        if (bytes == null) return -1;
        for (int i = bytes.length - 1; i >= 0; i--) {
            if (bytes[i] == b) return i;
        }
        return -1;
    }

    /**
     * Checks if a byte array contains a specific byte value.
     *
     * @param bytes the byte array to search
     * @param b the byte to search for
     * @return true if the byte is found; false otherwise
     */
    public static boolean contains(byte[] bytes, byte b) {
        return indexOf(bytes, b) >= 0;
    }

    /**
     * Checks if a byte array starts with the given prefix.
     *
     * @param bytes the byte array to check
     * @param prefix the prefix to match
     * @return true if bytes starts with prefix; false otherwise (including if either is null)
     */
    public static boolean startsWith(byte[] bytes, byte[] prefix) {
        if (bytes == null || prefix == null) return false;
        if (prefix.length > bytes.length) return false;
        for (int i = 0; i < prefix.length; i++) {
            if (bytes[i] != prefix[i]) return false;
        }
        return true;
    }

    /**
     * Checks if a byte array ends with the given suffix.
     *
     * @param bytes the byte array to check
     * @param suffix the suffix to match
     * @return true if bytes ends with suffix; false otherwise (including if either is null)
     */
    public static boolean endsWith(byte[] bytes, byte[] suffix) {
        if (bytes == null || suffix == null) return false;
        if (suffix.length > bytes.length) return false;
        int offset = bytes.length - suffix.length;
        for (int i = 0; i < suffix.length; i++) {
            if (bytes[offset + i] != suffix[i]) return false;
        }
        return true;
    }

    /**
     * Compares two byte arrays for equality.
     * Handles null values (both nulls are equal, nulls are not equal to non-null).
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both arrays are equal; false otherwise
     */
    public static boolean equals(byte[] a, byte[] b) {
        return Arrays.equals(a, b);
    }

    /**
     * Compares two byte arrays lexicographically (unsigned byte values).
     * Null is considered less than any non-null array.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return a negative value if a < b, zero if a == b, positive if a > b
     */
    public static int compare(byte[] a, byte[] b) {
        if (a == b) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        int len = Math.min(a.length, b.length);
        for (int i = 0; i < len; i++) {
            int unsignedA = a[i] & 0xFF;
            int unsignedB = b[i] & 0xFF;
            if (unsignedA != unsignedB) {
                return Integer.compare(unsignedA, unsignedB);
            }
        }
        return Integer.compare(a.length, b.length);
    }

    /**
     * Reverses the order of bytes in an array.
     *
     * @param bytes the byte array to reverse
     * @return a new byte array with bytes in reverse order, or null if bytes is null
     */
    public static byte[] reverse(byte[] bytes) {
        if (bytes == null) return null;
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[bytes.length - 1 - i];
        }
        return result;
    }

    /**
     * Compresses a byte array using GZIP compression.
     *
     * @param bytes the byte array to compress
     * @return the compressed byte array, or null if bytes is null
     * @throws Exception if compression fails
     */
    public static byte[] compress(byte[] bytes) throws Exception {
        if (bytes == null) return null;
        try (var bos = new ByteArrayOutputStream(bytes.length);
             var gzip = new GZIPOutputStream(bos)) {
            gzip.write(bytes);
            gzip.close();
            return bos.toByteArray();
        }
    }

    /**
     * Decompresses a byte array that was compressed using GZIP.
     *
     * @param bytes the compressed byte array to decompress
     * @return the decompressed byte array, or null if bytes is null
     * @throws Exception if decompression fails
     */
    public static byte[] decompress(byte[] bytes) throws Exception {
        if (bytes == null) return null;
        try (var bis = new ByteArrayInputStream(bytes);
             var gzip = new GZIPInputStream(bis);
             var bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzip.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        }
    }

}
