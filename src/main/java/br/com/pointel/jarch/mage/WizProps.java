package br.com.pointel.jarch.mage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WizProps {

    private WizProps() {
    }

    private static Logger log = LoggerFactory.getLogger(WizProps.class);
    private static final Properties props = new Properties();

    static {
        tryLoad();
    }

    public static void tryLoad() {
        try {
            load();
        } catch (Exception ex) {
            log.error("Error loading properties", ex);
        }
    }

    public static void load() throws Exception {
        load("props");
    }

    public static void load(String name) throws Exception {
        File file = new File(name + ".ini");
        if (file.exists()) {
            try (FileReader input = new FileReader(file)) {
                props.load(input);
            }
        }
    }

    public static Boolean get(String key, Boolean defaultValue) {
        return Boolean.valueOf(get(key, defaultValue.toString()));
    }

    public static void set(String key, Boolean value) {
        set(key, value.toString());
        trySave();
    }

    public static Integer get(String key, Integer defaultValue) {
        return Integer.valueOf(get(key, defaultValue.toString()));
    }

    public static void set(String key, Integer value) {
        set(key, value.toString());
        trySave();
    }

    public static Double get(String key, Double defaultValue) {
        return Double.valueOf(get(key, defaultValue.toString()));
    }

    public static void set(String key, Double value) {
        set(key, value.toString());
        trySave();
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static void set(String key, String value) {
        props.setProperty(key, value);
        trySave();
    }

    public static void trySave() {
        try {
            save();
        } catch (Exception ex) {
            log.error("Error saving properties", ex);
        }
    }

    public static void save() throws Exception {
        save("props");
    }

    public static void save(String name) throws Exception {
        File file = new File(name + ".ini");
        try (FileWriter output = new FileWriter(file)) {
            props.store(output, name + " properties");
        }
    }

}
