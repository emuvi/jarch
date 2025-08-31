package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import br.com.pointel.jarch.flow.FixVals;

public class KeyForeign implements Data, FixVals, Serializable {

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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof KeyForeign)) {
            return false;
        }
        KeyForeign keyForeign = (KeyForeign) o;
        return Objects.equals(inName, keyForeign.inName) && Objects.equals(outName, keyForeign.outName) && Objects.equals(outTableHead, keyForeign.outTableHead) && Objects.equals(matchList, keyForeign.matchList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inName, outName, outTableHead, matchList);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static KeyForeign fromChars(String chars) {
        return Data.fromChars(chars, KeyForeign.class);
    }

}
