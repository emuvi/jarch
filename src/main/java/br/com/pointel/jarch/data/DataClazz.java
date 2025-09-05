package br.com.pointel.jarch.data;

import br.com.pointel.jarch.mage.WizData;

public class DataClazz implements Data {

    public String data;
    public String clazz;

    public DataClazz() {
    }

    public DataClazz(Object value) {
        this.data = value == null ? null : WizData.toChars(value);
        this.clazz = value == null ? null : value.getClass().getCanonicalName();
    }

    public DataClazz(String data, String clazz) {
        this.data = data;
        this.clazz = clazz;
    }

    public Object getValue() throws Exception {
        return this.data == null ? null : WizData.fromChars(this.data, Class.forName( this.clazz));
    }

    public <T> T getValueOn(Class<T> clazz) throws Exception {
        return WizData.getOn(getValue(), clazz);
    }

    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    @Override
    public int hashCode() {
        return this.deepHash();
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static DataClazz fromChars(String chars) {
        return Base.fromChars(chars, DataClazz.class);
    }

}
