package com.vidlus.jarch.mage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A robust properties manager utility that handles reading, writing, parsing, 
 * and converting properties data securely. Supports auto-saving and classpath loading.
 */
public class WizProps {

    private WizProps() {
    }

    private static final Logger logger = LoggerFactory.getLogger(WizProps.class);

    private static final Properties props = new Properties();

    private static volatile File folder = new File(".");

    /**
     * Retrieves the current base directory where property files are stored.
     *
     * @return the folder location
     */
    public static File getFolder() {
        return folder;
    }

    /**
     * Sets the base directory for properties and immediately attempts to load the default properties file.
     *
     * @param folder the new base folder
     */
    public static void setFolder(File folder) {
        WizProps.folder = folder;
        tryLoad();
    }

    /**
     * Sets the base directory for properties and immediately attempts to load a specifically named properties file.
     *
     * @param folder the new base folder
     * @param name   the exact name of the properties file (without the .ini extension) to load
     */
    public static void setFolder(File folder, String name) {
        WizProps.folder = folder;
        tryLoad(name);
    }

    static {
        tryLoad();
    }

    /**
     * Silently attempts to load the default properties file (derived from WizLang.getName()).
     * Exceptions are caught and logged as errors.
     */
    public static void tryLoad() {
        try {
            load();
        } catch (Exception ex) {
            logger.error("Error loading properties", ex);
        }
    }

    /**
     * Silently attempts to load a specifically named properties file.
     * Exceptions are caught and logged as errors.
     *
     * @param name the properties filename (without the .ini extension)
     */
    public static void tryLoad(String name) {
        try {
            load(name);
        } catch (Exception ex) {
            logger.error("Error loading properties", ex);
        }
    }

    /**
     * Loads the default properties file (derived from WizLang.getName()).
     *
     * @throws Exception if an I/O error occurs during file reading
     */
    public static void load() throws Exception {
        load(WizLang.getName());
    }

    /**
     * Loads a specifically named properties file from the current active folder.
     * Expects an .ini extension.
     *
     * @param name the properties filename (without the .ini extension)
     * @throws Exception if an I/O error occurs during file reading
     */
    public static void load(String name) throws Exception {
        File file = new File(folder, name + ".ini");
        if (file.exists()) {
            try (FileReader input = new FileReader(file)) {
                props.load(input);
            }
        }
    }

    /**
     * Loads a properties file directly from the application classpath (e.g., resources folder).
     *
     * @param path the internal path to the resource file
     * @throws Exception if the resource cannot be found or read
     */
    public static void loadFromClasspath(String path) throws Exception {
        try (InputStream in = WizProps.class.getClassLoader().getResourceAsStream(path)) {
            if (in != null) {
                props.load(in);
            }
        }
    }

    /**
     * Silently attempts to save the current properties to the default file.
     * Exceptions are caught and logged as errors.
     */
    public static void trySave() {
        try {
            save();
        } catch (Exception ex) {
            logger.error("Error saving properties", ex);
        }
    }

    /**
     * Saves the current properties state into the default file (derived from WizLang.getName()).
     *
     * @throws Exception if an I/O error occurs during writing
     */
    public static void save() throws Exception {
        save(WizLang.getName());
    }

    /**
     * Saves the current properties state into a specifically named file inside the active folder.
     *
     * @param name the target filename (without the .ini extension)
     * @throws Exception if an I/O error occurs during writing
     */
    public static void save(String name) throws Exception {
        File file = new File(folder, name + ".ini");
        try (FileWriter output = new FileWriter(file)) {
            props.store(output, name + " properties");
        }
    }

    // =========================================================================
    // PROPERTY ACCESS (GETTERS)
    // =========================================================================

    /**
     * Checks if the given property key currently exists.
     *
     * @param key the property key to search for
     * @return true if the property is defined, false otherwise
     */
    public static boolean containsKey(String key) {
        return props.containsKey(key);
    }

    /**
     * Retrieves a String property, falling back to a default if missing.
     *
     * @param key          the property key
     * @param defaultValue the fallback value
     * @return the property value, or defaultValue if the key does not exist
     */
    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    /**
     * Retrieves a Boolean property, safely falling back to a default if missing or unparseable.
     *
     * @param key          the property key
     * @param defaultValue the fallback value
     * @return the Boolean value
     */
    public static Boolean get(String key, Boolean defaultValue) {
        return Boolean.valueOf(get(key, defaultValue.toString()));
    }

    /**
     * Retrieves an Integer property, safely catching format exceptions and falling back to a default.
     *
     * @param key          the property key
     * @param defaultValue the fallback value
     * @return the Integer value
     */
    public static Integer get(String key, Integer defaultValue) {
        try {
            return Integer.valueOf(get(key, defaultValue.toString()));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Retrieves a Long property, safely catching format exceptions and falling back to a default.
     *
     * @param key          the property key
     * @param defaultValue the fallback value
     * @return the Long value
     */
    public static Long get(String key, Long defaultValue) {
        try {
            return Long.valueOf(get(key, defaultValue.toString()));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Retrieves a Double property, safely catching format exceptions and falling back to a default.
     *
     * @param key          the property key
     * @param defaultValue the fallback value
     * @return the Double value
     */
    public static Double get(String key, Double defaultValue) {
        try {
            return Double.valueOf(get(key, defaultValue.toString()));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Retrieves a Float property, safely catching format exceptions and falling back to a default.
     *
     * @param key          the property key
     * @param defaultValue the fallback value
     * @return the Float value
     */
    public static Float get(String key, Float defaultValue) {
        try {
            return Float.valueOf(get(key, defaultValue.toString()));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Returns a full copy of the currently loaded properties as a standard Map.
     * Modifications to the returned map do not affect the internal properties.
     *
     * @return a LinkedHashMap containing all active properties
     */
    public static Map<String, String> getAll() {
        Map<String, String> map = new LinkedHashMap<>();
        for (String key : props.stringPropertyNames()) {
            map.put(key, props.getProperty(key));
        }
        return map;
    }

    // =========================================================================
    // PROPERTY MODIFICATION (SETTERS)
    // =========================================================================

    /**
     * Sets a String property and automatically triggers a background save.
     *
     * @param key   the property key
     * @param value the new string value
     */
    public static void set(String key, String value) {
        props.setProperty(key, value);
        trySave();
    }

    /**
     * Sets a Boolean property and triggers an auto-save.
     *
     * @param key   the property key
     * @param value the boolean value to store
     */
    public static void set(String key, Boolean value) {
        set(key, value.toString());
    }

    /**
     * Sets an Integer property and triggers an auto-save.
     *
     * @param key   the property key
     * @param value the integer value to store
     */
    public static void set(String key, Integer value) {
        set(key, value.toString());
    }

    /**
     * Sets a Long property and triggers an auto-save.
     *
     * @param key   the property key
     * @param value the long value to store
     */
    public static void set(String key, Long value) {
        set(key, value.toString());
    }

    /**
     * Sets a Double property and triggers an auto-save.
     *
     * @param key   the property key
     * @param value the double value to store
     */
    public static void set(String key, Double value) {
        set(key, value.toString());
    }

    /**
     * Sets a Float property and triggers an auto-save.
     *
     * @param key   the property key
     * @param value the float value to store
     */
    public static void set(String key, Float value) {
        set(key, value.toString());
    }

    /**
     * Removes a specific property key and triggers an auto-save.
     *
     * @param key the key to remove
     */
    public static void remove(String key) {
        props.remove(key);
        trySave();
    }

    /**
     * Clears all loaded properties and triggers an auto-save.
     */
    public static void clear() {
        props.clear();
        trySave();
    }

    /**
     * Merges an entire map of key-value pairs into the properties and triggers an auto-save.
     *
     * @param map the map of properties to inject
     */
    public static void putAll(Map<String, String> map) {
        if (map == null) return;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            props.setProperty(entry.getKey(), entry.getValue());
        }
        trySave();
    }

    // =========================================================================
    // UTILS (MAP -> STRING)
    // =========================================================================

    /**
     * Parses a raw string block containing delimited key-value pairs into a Map.
     * Expects "=" as the default separator.
     *
     * @param source the raw text block to parse
     * @return a Map containing the extracted properties
     */
    public static Map<String, String> fromSource(String source) {
        return fromSource(source, "=");
    }

    /**
     * Parses a raw string block containing delimited key-value pairs into a Map using a custom separator.
     * Ignores empty lines and lines starting with "#".
     *
     * @param source    the raw text block to parse
     * @param separator the delimiter string separating keys from values
     * @return a Map containing the extracted properties
     */
    public static Map<String, String> fromSource(String source, String separator) {
        var result = new LinkedHashMap<String, String>();
        if (WizString.isEmpty(source)) {
            return result;
        }
        var lines = WizString.getLines(source);
        for (var line : lines) {
            if (line.isBlank() || line.startsWith("#")) {
                continue;
            }
            int pos = line.indexOf(separator);
            if (pos > -1) {
                result.put(line.substring(0, pos).trim(), line.substring(pos + separator.length()).trim());
            }
        }
        return result;
    }

    /**
     * Serializes a Map of properties into a standard raw string block.
     * Uses "=" as the default separator and injects default header comments.
     *
     * @param props the property map to dump
     * @return the serialized text block
     */
    public static String toSource(Map<String, String> props) {
        return toSource(props, "=");
    }

    /**
     * Serializes a Map of properties into a raw string block using a custom separator.
     * Injects default header comments.
     *
     * @param props     the property map to dump
     * @param separator the delimiter string separating keys from values
     * @return the serialized text block
     */
    public static String toSource(Map<String, String> props, String separator) {
        return toSource(props, separator, getPropsDefaultComments());
    }

    /**
     * Serializes a Map of properties into a raw string block using a custom separator and custom header comments.
     *
     * @param props     the property map to dump
     * @param separator the delimiter string separating keys from values
     * @param comments  an array of strings to place at the top of the block as comments
     * @return the serialized text block
     */
    public static String toSource(Map<String, String> props, String separator, String[] comments) {
        var result = new StringBuilder();
        for (var comment : comments) {
            if (comment == null || comment.isBlank()) {
                continue;
            }
            if (!comment.startsWith("#")) {
                result.append("# ");
            }
            result.append(comment);
            result.append("\n");
        }
        for (var key : props.keySet()) {
            result.append(key);
            result.append(separator);
            result.append(props.get(key));
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Generates the standard default comments appended to serialized property files.
     * Stamped with the class name and current file-formatted timestamp.
     *
     * @return an array of comment strings
     */
    public static String[] getPropsDefaultComments() {
        return new String[] {
                "# Generated by: " + WizLang.getName(),
                "# Created on: " + WizInstant.formatDateTimeFile.format(Instant.now())
        };
    }

}
