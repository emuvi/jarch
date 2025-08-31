package br.com.pointel.jarch.data;

import com.google.gson.Gson;

public interface Data {

    public static final Gson gson = new Gson();

    public default String toChars() {
        return gson.toJson(this);
    }

    public static <T> T fromChars(String chars, Class<T> clazz) {
        return gson.fromJson(chars, clazz);
    }

}
