package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;
import br.com.pointel.jarch.mage.WizData;

public class Field implements FixVals {

    public String name;
    public Nature nature;
    public Integer size;
    public Integer precision;
    public Boolean notNull;
    public Boolean key;

    public Field() {}

    public Field(String name) {
        this.name = name;
    }

    public Field(String name, Nature nature) {
        this.name = name;
        this.nature = nature;
    }

    public Field(String name, Nature nature, Boolean notNull) {
        this.name = name;
        this.nature = nature;
        this.notNull = notNull;
    }

    public Field(String name, Nature nature, Integer size) {
        this.name = name;
        this.nature = nature;
        this.size = size;
    }

    public Field(String name, Nature nature, Integer size, Boolean notNull) {
        this.name = name;
        this.nature = nature;
        this.size = size;
        this.notNull = notNull;
    }

    public Field(String name, Nature nature, Integer size, Integer precision) {
        this.name = name;
        this.nature = nature;
        this.size = size;
        this.precision = precision;
    }

    public Field(String name, Nature nature, Integer size, Integer precision, Boolean notNull) {
        this.name = name;
        this.nature = nature;
        this.size = size;
        this.precision = precision;
        this.notNull = notNull;
    }

    public Field(String name, Nature nature, Integer size, Integer precision, Boolean notNull,
                    Boolean key) {
        this.name = name;
        this.nature = nature;
        this.size = size;
        this.precision = precision;
        this.notNull = notNull;
        this.key = key;
    }

    public Object getValueFrom(String formatted) throws Exception {
        return WizData.getValueFrom(this.nature, formatted);
    }

    public String formatValue(Object value) throws Exception {
        return WizData.formatValue(this.nature, value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Field)) {
            return false;
        }
        Field field = (Field) o;
        return Objects.equals(name, field.name)
                        && Objects.equals(nature, field.nature)
                        && Objects.equals(size, field.size)
                        && Objects.equals(precision, field.precision)
                        && Objects.equals(notNull, field.notNull)
                        && Objects.equals(key, field.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nature, size, precision, notNull, key);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Field fromString(String json) {
        return new Gson().fromJson(json, Field.class);
    }

}
