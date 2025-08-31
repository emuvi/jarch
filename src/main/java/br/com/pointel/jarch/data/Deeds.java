package br.com.pointel.jarch.data;

public enum Deeds {

    SELECT(false), INSERT(true), UPDATE(true), DELETE(true);

    public final boolean mutates;

    private Deeds(boolean mutates) {
        this.mutates = mutates;
    }
}
