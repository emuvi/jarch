package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.ArrayList;
import br.com.pointel.jarch.flow.FixVals;

public class Heads extends ArrayList<TableHead> implements Data, FixVals, Serializable {

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
