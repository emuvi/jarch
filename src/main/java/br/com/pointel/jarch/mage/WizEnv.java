package br.com.pointel.jarch.mage;

public class WizEnv {

    public static String get(String key, String defaultValue) {
        var value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    public static Integer get(String key, Integer defaultValue) {
        var value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : Integer.parseInt(value);
    }

}
