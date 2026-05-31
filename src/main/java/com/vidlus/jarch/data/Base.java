package com.vidlus.jarch.data;

import java.io.Serializable;

import com.vidlus.jarch.mage.WizData;
import com.vidlus.jarch.mage.WizLang;

public interface Base extends Serializable {

    public default Base deepClone() {
        return WizLang.deepClone(this);
    }

    public default boolean deepEquals(Object that) {
        return WizLang.deepEquals(this, that);
    }

    public default int deepHash() {
        return WizLang.deepHash(this);
    }

    public default String toChars() {
        return WizData.toJson(this);
    }

    public static <T> T fromChars(String chars, Class<T> clazz) {
        return WizData.fromJson(chars, clazz);
    }

}
