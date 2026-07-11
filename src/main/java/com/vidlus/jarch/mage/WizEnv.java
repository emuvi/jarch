package com.vidlus.jarch.mage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class WizEnv {

    private WizEnv() {
    }

    // =========================================================================
    // ENVIRONMENT VARIABLES (System.getenv)
    // =========================================================================

    /**
     * Gets an environment variable, returning null if it is missing or blank.
     */
    public static String get(String key) {
        var value = System.getenv(key);
        return value == null || value.isBlank() ? null : value;
    }

    /**
     * Gets an environment variable, returning the default if missing or blank.
     */
    public static String get(String key, String defaultValue) {
        var value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    /**
     * Gets an environment variable as an Integer, returning the default if missing, blank, or invalid.
     */
    public static Integer getInt(String key, Integer defaultValue) {
        var value = System.getenv(key);
        if (value == null || value.isBlank()) return defaultValue;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets an environment variable as a Long, returning the default if missing, blank, or invalid.
     */
    public static Long getLong(String key, Long defaultValue) {
        var value = System.getenv(key);
        if (value == null || value.isBlank()) return defaultValue;
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets an environment variable as a Double, returning the default if missing, blank, or invalid.
     */
    public static Double getDouble(String key, Double defaultValue) {
        var value = System.getenv(key);
        if (value == null || value.isBlank()) return defaultValue;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Gets an environment variable as a Boolean, returning the default if missing or blank.
     * "true", "1", "yes", "on" (case-insensitive) are considered true.
     */
    public static Boolean getBoolean(String key, Boolean defaultValue) {
        var value = System.getenv(key);
        if (value == null || value.isBlank()) return defaultValue;
        String val = value.trim().toLowerCase();
        return val.equals("true") || val.equals("1") || val.equals("yes") || val.equals("on");
    }

    /**
     * Gets an environment variable, throwing an exception if it is missing or blank.
     */
    public static String require(String key) {
        var value = get(key);
        if (value == null) {
            throw new IllegalStateException("Required environment variable is missing: " + key);
        }
        return value;
    }

    /**
     * Returns a map of all system environment variables.
     */
    public static Map<String, String> getAll() {
        return System.getenv();
    }

    // =========================================================================
    // SYSTEM PROPERTIES (System.getProperty)
    // =========================================================================

    /**
     * Gets a system property, returning null if missing or blank.
     */
    public static String getProperty(String key) {
        var value = System.getProperty(key);
        return value == null || value.isBlank() ? null : value;
    }

    /**
     * Gets a system property, returning the default if missing or blank.
     */
    public static String getProperty(String key, String defaultValue) {
        var value = System.getProperty(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    /**
     * Gets a system property as a Boolean, returning the default if missing or blank.
     */
    public static Boolean getPropertyBoolean(String key, Boolean defaultValue) {
        var value = System.getProperty(key);
        if (value == null || value.isBlank()) return defaultValue;
        String val = value.trim().toLowerCase();
        return val.equals("true") || val.equals("1") || val.equals("yes") || val.equals("on");
    }

    /**
     * Gets a system property, throwing an exception if it is missing or blank.
     */
    public static String requireProperty(String key) {
        var value = getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Required system property is missing: " + key);
        }
        return value;
    }

    /**
     * Returns all system properties.
     */
    public static Properties getProperties() {
        return System.getProperties();
    }

    // =========================================================================
    // COMMON SYSTEM DIRECTORIES & INFO
    // =========================================================================

    /**
     * Gets the system temporary directory path.
     */
    public static String getTempDir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Gets the system temporary directory as a File object.
     */
    public static File getTempDirFile() {
        return new File(getTempDir());
    }

    /**
     * Gets the current user's home directory path.
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * Gets the current user's home directory as a Path object.
     */
    public static Path getUserHomePath() {
        return Paths.get(getUserHome());
    }

    /**
     * Gets the current working directory path (user.dir).
     */
    public static String getWorkDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Gets the current working directory as a File object.
     */
    public static File getWorkDirFile() {
        return new File(getWorkDir());
    }

    /**
     * Gets the current operating system user name.
     */
    public static String getUserName() {
        return System.getProperty("user.name");
    }

    /**
     * Gets the operating system name (e.g., Windows 10, Linux, Mac OS X).
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * Gets the operating system version.
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * Gets the Java runtime version.
     */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Gets the file separator for the current OS (e.g., "\" on Windows, "/" on Unix).
     */
    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }
    
    /**
     * Gets the path separator for the current OS (e.g., ";" on Windows, ":" on Unix).
     */
    public static String getPathSeparator() {
        return System.getProperty("path.separator");
    }

    /**
     * Gets the line separator for the current OS.
     */
    public static String getLineSeparator() {
        return System.lineSeparator();
    }

}
