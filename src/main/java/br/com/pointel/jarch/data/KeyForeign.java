package br.com.pointel.jarch.data;

import java.util.List;

public class KeyForeign implements Data {

    public String inName;
    public String outName;
    public TableHead outTableHead;
    public List<Match> matchList;

    public KeyForeign() {
    }

    public KeyForeign(String inName) {
        this.inName = inName;
    }

    public KeyForeign(String inName, String outName) {
        this.inName = inName;
        this.outName = outName;
    }

    public KeyForeign(String inName, String outName, TableHead outTableHead) {
        this.inName = inName;
        this.outName = outName;
        this.outTableHead = outTableHead;
    }

    public KeyForeign(String inName, TableHead outTableHead) {
        this.inName = inName;
        this.outTableHead = outTableHead;
    }

    public KeyForeign(TableHead outTableHead) {
        this.outTableHead = outTableHead;
    }

    public KeyForeign(String inName, TableHead outTableHead, List<Match> matchList) {
        this.inName = inName;
        this.outTableHead = outTableHead;
        this.matchList = matchList;
    }

    public KeyForeign(TableHead outTableHead, List<Match> matchList) {
        this.outTableHead = outTableHead;
        this.matchList = matchList;
    }

    public KeyForeign(String inName, String outName, TableHead outTableHead, List<Match> matchList) {
        this.inName = inName;
        this.outName = outName;
        this.outTableHead = outTableHead;
        this.matchList = matchList;
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

    public static KeyForeign fromChars(String chars) {
        return Data.fromChars(chars, KeyForeign.class);
    }

}
