package br.com.pointel.jarch.data;

public enum CrudDeeds {

    SELECT(false), INSERT(true), UPDATE(true), DELETE(true);

    public final boolean mutates;

    private CrudDeeds(boolean mutates) {
        this.mutates = mutates;
    }
}
