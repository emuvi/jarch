package com.vidlus.jarch.data;

import com.vidlus.jarch.flow.FixVals;
import com.vidlus.jarch.mage.WizData;

public interface Data extends Base, FixVals {

    public static <T> T fromChars(String chars, Class<T> clazz) {
        return WizData.fromJson(chars, clazz);
    }

}
