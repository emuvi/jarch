package br.com.pointel.jarch.data;

public class ToUpdate implements Data {

    public String base;
    public Update update;

    public ToUpdate() {}

    public ToUpdate(String base) {
        this.base = base;
    }

    public ToUpdate(Update update) {
        this.update = update;
    }

    public ToUpdate(String base, Update update) {
        this.base = base;
        this.update = update;
    }

    public Registry getRegistry() {
        return new Registry(base, update.tableHead);
    }

    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    @Override
    public int hashCode() {
        return this.deepHash();
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static ToUpdate fromChars(String chars) {
        return Base.fromChars(chars, ToUpdate.class);
    }

}
