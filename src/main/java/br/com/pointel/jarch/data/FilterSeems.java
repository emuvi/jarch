package br.com.pointel.jarch.data;

public enum FilterSeems implements Data {

    Is, IsNot;

    @Override
    public String toString() {
        return this.toChars();
    }

    public static FilterSeems fromChars(String chars) {
        return Data.fromChars(chars, FilterSeems.class);
    }

}
