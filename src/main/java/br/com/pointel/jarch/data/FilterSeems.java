package br.com.pointel.jarch.data;

import java.io.Serializable;

public enum FilterSeems implements Data, Serializable {

    Is, IsNot;

    @Override
    public String toString() {
        return this.toChars();
    }

    public static FilterSeems fromChars(String chars) {
        return Data.fromChars(chars, FilterSeems.class);
    }

}
