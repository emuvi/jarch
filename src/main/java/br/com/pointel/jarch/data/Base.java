package br.com.pointel.jarch.data;

import java.io.Serializable;
import br.com.pointel.jarch.mage.WizData;
import br.com.pointel.jarch.mage.WizLang;

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
