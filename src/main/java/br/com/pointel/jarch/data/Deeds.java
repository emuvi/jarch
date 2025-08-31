package br.com.pointel.jarch.data;

public enum Deeds {

    Select(false), 
    Insert(true), 
    Update(true), 
    Delete(true);

    public final boolean mutates;

    private Deeds(boolean mutates) {
        this.mutates = mutates;
    }
}
