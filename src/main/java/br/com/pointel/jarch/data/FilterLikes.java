package br.com.pointel.jarch.data;

import java.io.Serializable;

public enum FilterLikes implements Data, Serializable {

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
