package br.com.pointel.jarch.data;

public enum FilterTies implements Data {

    And, Or;

    @Override
    public String toString() {
        return this.toChars();
    }

    public static FilterTies fromChars(String chars) {
        return Data.fromChars(chars, FilterTies.class);
    }

}
