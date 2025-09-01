package br.com.pointel.jarch.data;

public enum Deeds implements Data {

    Select(false), 
    Insert(true), 
    Update(true), 
    Delete(true);

    public final boolean mutates;

    private Deeds(boolean mutates) {
        this.mutates = mutates;
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Deeds fromChars(String chars) {
        return Data.fromChars(chars, Deeds.class);
    }
    
}
