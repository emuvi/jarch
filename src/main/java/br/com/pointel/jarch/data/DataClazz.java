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

    public boolean hasData() {
        return this.data != null;
    }

    public boolean hasClazz() {
        return this.clazz != null;
    }

    public DataClazz withData(String data) {
        this.data = data;
        return this;
    }

    public DataClazz withNoData() {
        this.data = null;
        return this;
    }

    public DataClazz withClazz(String clazz) {
        this.clazz = clazz;
        return this;
    }

    public DataClazz withNoClazz() {
        this.clazz = null;
        return this;
    }

    public DataClazz uponData(String data) {
        var clone = this.clone();
        clone.data = data;
        return clone;
    }

    public DataClazz uponNoData() {
        var clone = this.clone();
        clone.data = null;
        return clone;
    }

    public DataClazz uponClazz(String clazz) {
        var clone = this.clone();
        clone.clazz = clazz;
        return clone;
    }

    public DataClazz uponNoClazz() {
        var clone = this.clone();
        clone.clazz = null;
        return clone;
    }

    @Override
    public DataClazz clone() {
        return (DataClazz) this.deepClone();
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

    public Object getValue() throws Exception {
        return this.data == null ? null : WizData.fromChars(this.data, Class.forName( this.clazz));
    }

    public <T> T getValueOn(Class<T> clazz) throws Exception {
        return WizData.getOn(getValue(), clazz);
    }

}
