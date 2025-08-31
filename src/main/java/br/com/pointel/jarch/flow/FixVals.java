package br.com.pointel.jarch.flow;

import java.lang.reflect.Modifier;
import com.google.gson.Gson;
import br.com.pointel.jarch.mage.WizChars;
import br.com.pointel.jarch.mage.WizLang;

public interface FixVals {

    public default void fixNullsAndEnvs() throws Exception {
        fixNulls();
        for (var field : this.getClass().getDeclaredFields()) {
            var mods = field.getModifiers();
            if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
                continue;
            }
            var value = field.get(this);
            if (value instanceof String strValue) {
                value = WizChars.replaceEnvVars(strValue);
                WizLang.forceSetField(field, this, value);
            }
        }
    }

    public default void fixNulls() throws Exception {
        for (var field : this.getClass().getDeclaredFields()) {
            var mods = field.getModifiers();
            if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
                continue;
            }
            var value = field.get(this);
            if (value == null || value.toString().isEmpty()) {
                if (field.isAnnotationPresent(FixBool.class)) {
                    value = field.getAnnotation(FixBool.class).value();
                } else if (field.isAnnotationPresent(FixByte.class)) {
                    value = field.getAnnotation(FixByte.class).value();
                } else if (field.isAnnotationPresent(FixChar.class)) {
                    value = field.getAnnotation(FixChar.class).value();
                } else if (field.isAnnotationPresent(FixChars.class)) {
                    value = field.getAnnotation(FixChars.class).value();
                } else if (field.isAnnotationPresent(FixDouble.class)) {
                    value = field.getAnnotation(FixDouble.class).value();
                } else if (field.isAnnotationPresent(FixFloat.class)) {
                    value = field.getAnnotation(FixFloat.class).value();
                } else if (field.isAnnotationPresent(FixInt.class)) {
                    value = field.getAnnotation(FixInt.class).value();
                } else if (field.isAnnotationPresent(FixLong.class)) {
                    value = field.getAnnotation(FixLong.class).value();
                } else if (field.isAnnotationPresent(FixShort.class)) {
                    value = field.getAnnotation(FixShort.class).value();
                } else if (field.isAnnotationPresent(FixObject.class)) {
                    var valueStr = field.getAnnotation(FixObject.class).value();
                    var valueType = field.getAnnotation(FixObject.class).type();
                    value = new Gson().fromJson(valueStr, valueType);
                } else {
                    value = field.getType().getConstructor().newInstance();
                }
                WizLang.forceSetField(field, this, value);
            }
        }
        fixDefaults();
    }

    public default void fixDefaults() throws Exception {}

}
