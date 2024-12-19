package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

public class Join implements FixVals {

    public TableHead tableHead;
    public String alias;
    public List<Filter> filterList;
    public JoinTie joinTie;

    public Join() {}

    public Join(JoinTie joinTie) {
        this.joinTie = joinTie;
    }

    public Join(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Join(TableHead tableHead, String alias) {
        this.tableHead = tableHead;
        this.alias = alias;
    }

    public Join(TableHead tableHead, JoinTie joinTie) {
        this.tableHead = tableHead;
        this.joinTie = joinTie;
    }

    public Join(TableHead tableHead, String alias, JoinTie joinTie) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.joinTie = joinTie;
    }

    public Join(TableHead tableHead, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.filterList = filterList;
    }

    public Join(TableHead tableHead, String alias, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.filterList = filterList;
    }

    public Join(TableHead tableHead, List<Filter> filterList, JoinTie joinTie) {
        this.tableHead = tableHead;
        this.filterList = filterList;
        this.joinTie = joinTie;
    }

    public Join(TableHead tableHead, String alias, List<Filter> filterList, JoinTie joinTie) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.filterList = filterList;
        this.joinTie = joinTie;
    }

    public boolean hasFilters() {
        return this.filterList != null && !this.filterList.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Join)) {
            return false;
        }
        Join joined = (Join) o;
        return Objects.equals(tableHead, joined.tableHead)
                        && Objects.equals(alias, joined.alias)
                        && Objects.equals(filterList, joined.filterList)
                        && Objects.equals(joinTie, joined.joinTie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHead, alias, filterList, joinTie);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Join fromString(String json) {
        return new Gson().fromJson(json, Join.class);
    }

}
