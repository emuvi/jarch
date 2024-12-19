package br.com.pointel.jarch.flow;

import java.lang.reflect.Modifier;
import br.com.pointel.jarch.mage.WizChars;

public interface FixVals {

    public default void fixNulls() throws Exception {
        for (var field : this.getClass().getDeclaredFields()) {
            var mods = field.getModifiers();
            if (Modifier.isStatic(mods)
                            || Modifier.isFinal(mods)
                            || Modifier.isTransient(mods)) {
                continue;
            }
            var value = field.get(this);
            if (value == null) {
                value = field.getType().getConstructor().newInstance();
                field.set(this, value);
            }
        }
    }

    public default void fixNullsAndEnvs() throws Exception {
        for (var field : this.getClass().getDeclaredFields()) {
            var mods = field.getModifiers();
            if (Modifier.isStatic(mods)
                            || Modifier.isFinal(mods)
                            || Modifier.isTransient(mods)) {
                continue;
            }
            var value = field.get(this);
            if (value == null) {
                value = field.getType().getConstructor().newInstance();
                field.set(this, value);
            }
            if (value instanceof String strValue) {
                value = WizChars.replaceEnvVars(strValue);
                field.set(this, value);
            }
        }
    }

}
