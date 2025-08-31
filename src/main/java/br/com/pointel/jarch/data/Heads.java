package br.com.pointel.jarch.data;

import java.util.ArrayList;

public class Heads extends ArrayList<TableHead> implements Data {

    public Heads() {
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Heads fromChars(String chars) {
        return Data.fromChars(chars, Heads.class);
    }

}
