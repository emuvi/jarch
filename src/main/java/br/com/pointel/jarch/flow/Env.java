package br.com.pointel.jarch.flow;

public class Env {

    public static String get(String key, String defaultValue) {
        var value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }

}
