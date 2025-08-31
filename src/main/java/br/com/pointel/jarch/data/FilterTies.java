package br.com.pointel.jarch.data;

import java.io.Serializable;

public enum FilterTies implements Data, Serializable {

    And, Or;

    @Override
    public String toString() {
        return this.toChars();
    }

    public static FilterTies fromChars(String chars) {
        return Data.fromChars(chars, FilterTies.class);
    }

}
