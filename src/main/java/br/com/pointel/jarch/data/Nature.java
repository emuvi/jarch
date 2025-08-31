package br.com.pointel.jarch.data;

import java.io.Serializable;

public enum Nature implements Data, Serializable {

    Bit, Bool, Byte,

    Tiny, Small, Int, Long,

    Serial, BigSerial,

    Float, Real, Double,

    Numeric, BigNumeric,

    Char, Chars,

    Date, Time, DateTime, Timestamp,

    Bytes, Blob, Text, Object;


    @Override
    public String toString() {
        return this.toChars();
    }

    public static Nature fromChars(String chars) {
        return Data.fromChars(chars, Nature.class);
    }

}
