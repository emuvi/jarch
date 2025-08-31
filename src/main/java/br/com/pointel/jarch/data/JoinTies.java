package br.com.pointel.jarch.data;

import java.io.Serializable;

public enum JoinTies implements Data, Serializable {

    Inner, Left, Right, Full, Cross;

    @Override
    public String toString() {
        return this.toChars();
    }

    public static JoinTies fromChars(String chars) {
        return Data.fromChars(chars, JoinTies.class);
    }

}
