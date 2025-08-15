package br.com.pointel.jarch.data;

import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;
import java.util.Objects;

public class Match implements FixVals {

    public Integer ord;
    public String inColumn;
    public String outColumn;

    public Match() {
    }

    public Match(String inColumn) {
        this.inColumn = inColumn;
    }

    public Match(Integer ord, String inColumn) {
        this.ord = ord;
        this.inColumn = inColumn;
    }

    public Match(String inColumn, String outColumn) {
        this.inColumn = inColumn;
        this.outColumn = outColumn;
    }

    public Match(Integer ord, String inColumn, String outColumn) {
        this.ord = ord;
        this.inColumn = inColumn;
        this.outColumn = outColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Match)) {
            return false;
        }
        Match match = (Match) o;
        return Objects.equals(ord, match.ord) && Objects.equals(inColumn, match.inColumn) && Objects.equals(outColumn, match.outColumn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ord, inColumn, outColumn);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Match fromString(String json) {
        return new Gson().fromJson(json, Match.class);
    }

}
