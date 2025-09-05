package br.com.pointel.jarch.data;

import br.com.pointel.jarch.flow.FixVals;
import br.com.pointel.jarch.mage.WizData;

public interface Data extends Base, FixVals {

    public static <T> T fromChars(String chars, Class<T> clazz) {
        return WizData.fromChars(chars, clazz);
    }

}
