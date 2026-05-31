package com.vidlus.jarch.flow;

import java.io.Serializable;

import com.vidlus.jarch.mage.WizArray;
import com.vidlus.jarch.mage.WizLang;

public interface FixVals extends Serializable {

    public default void fixNullsAndEnvs() throws Exception {
        fixNulls();
        fixEnvs();
    }

    public default void fixNulls() throws Exception {
        if (WizArray.is(this)) {
            for (var item : WizArray.get(this)) {
                if (item instanceof FixVals fixable) {
                    fixable.fixNulls();
                }
            }
        } else {
            WizLang.fixNullsOnMembers(this);
        }
        fixDefaults();
    }

    public default void fixDefaults() throws Exception {}

    public default void fixEnvs() throws Exception {
        if (WizArray.is(this)) {
            for (var item : WizArray.get(this)) {
                if (item instanceof FixVals fixable) {
                    fixable.fixEnvs();
                }
            }
        } else {
            WizLang.fixEnvsOnMembers(this);
        }
    }

}
