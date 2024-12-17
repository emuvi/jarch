package br.com.pointel.jarch.data;

public enum DeedsCrud {

    SELECT(false), INSERT(true), UPDATE(true), DELETE(true);

    public final boolean mutates;

    private DeedsCrud(boolean mutates) {
        this.mutates = mutates;
    }
}
