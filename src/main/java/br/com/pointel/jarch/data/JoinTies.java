package br.com.pointel.jarch.data;

public enum JoinTies implements Data {

    Inner, Left, Right, Full, Cross;

    @Override
    public String toString() {
        return this.toChars();
    }

    public static JoinTies fromChars(String chars) {
        return Data.fromChars(chars, JoinTies.class);
    }

}
