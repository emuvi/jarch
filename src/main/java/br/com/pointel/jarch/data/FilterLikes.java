package br.com.pointel.jarch.data;

public enum FilterLikes implements Data {

    Equals,

    Bigger, Lesser,

    BiggerOrEquals, LesserOrEquals,

    StartsWith, EndsWith,

    Contains;

    @Override
    public String toString() {
        return this.toChars();
    }

    public static FilterLikes fromChars(String chars) {
        return Data.fromChars(chars, FilterLikes.class);
    }

}
