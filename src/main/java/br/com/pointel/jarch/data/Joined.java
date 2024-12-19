package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

public class Joined implements FixVals {

    public TableHead tableHead;
    public String alias;
    public List<Filter> filterList;
    public JoinedTies ties;

    public Joined() {}

    public Joined(JoinedTies ties) {
        this.ties = ties;
    }

    public Joined(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Joined(TableHead tableHead, String alias) {
        this.tableHead = tableHead;
        this.alias = alias;
    }

    public Joined(TableHead tableHead, JoinedTies ties) {
        this.tableHead = tableHead;
        this.ties = ties;
    }

    public Joined(TableHead tableHead, String alias, JoinedTies ties) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.ties = ties;
    }

    public Joined(TableHead tableHead, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.filterList = filterList;
    }

    public Joined(TableHead tableHead, String alias, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.filterList = filterList;
    }

    public Joined(TableHead tableHead, List<Filter> filterList, JoinedTies ties) {
        this.tableHead = tableHead;
        this.filterList = filterList;
        this.ties = ties;
    }

    public Joined(TableHead tableHead, String alias, List<Filter> filterList, JoinedTies ties) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.filterList = filterList;
        this.ties = ties;
    }

    public boolean hasFilters() {
        return this.filterList != null && !this.filterList.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Joined)) {
            return false;
        }
        Joined joined = (Joined) o;
        return Objects.equals(tableHead, joined.tableHead)
                        && Objects.equals(alias, joined.alias)
                        && Objects.equals(filterList, joined.filterList)
                        && Objects.equals(ties, joined.ties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHead, alias, filterList, ties);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Joined fromString(String json) {
        return new Gson().fromJson(json, Joined.class);
    }

}
