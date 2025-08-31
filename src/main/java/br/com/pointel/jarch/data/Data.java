package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;
import br.com.pointel.jarch.mage.WizArray;
import br.com.pointel.jarch.mage.WizLang;

public interface Data extends FixVals {

    public static final Gson gson = new Gson();

    public default boolean deepEquals(Object that) {
        try {
            if (that == null) return false;
            if (that == this) return true;
            if (!this.getClass().equals(that.getClass())) return false;
            if (WizArray.is(this)) {
                final var thisValues = WizArray.get(this);
                final var thatValues = WizArray.get(that);
                return Objects.deepEquals(thisValues, thatValues);
            } else {
                final var thisValues = WizLang.getValuesFromMembers(this);
                final var thatValues = WizLang.getValuesFromMembers(that);
                return Objects.deepEquals(thisValues, thatValues);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public default int deepHash() {
        try {
            if (WizArray.is(this)) {
                final var thisValues = WizArray.get(this);
                return Objects.hash(thisValues);
            } else {
                final var thisValues = WizLang.getValuesFromMembers(this);
                return Objects.hash(thisValues);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public default String toChars() {
        return gson.toJson(this);
    }

    public static <T> T fromChars(String chars, Class<T> clazz) {
        return gson.fromJson(chars, clazz);
    }

}
