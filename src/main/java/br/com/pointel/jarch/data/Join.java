package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class Join implements FixVals {
    public TableHead tableHead;
    public String alias;
    public List<Filter> filterList;
    public JoinTies ties;

    public Join() {}

    public Join(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Join(TableHead tableHead, String alias) {
        this.tableHead = tableHead;
        this.alias = alias;
    }

    public Join(TableHead tableHead, JoinTies ties) {
        this.tableHead = tableHead;
        this.ties = ties;
    }

    public Join(TableHead tableHead, String alias, JoinTies ties) {
        this.tableHead = tableHead;
        this.alias = alias;
        this.ties = ties;
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

    public Join(TableHead tableHead, List<Filter> filterList, JoinTies ties) {
        this.tableHead = tableHead;
        this.filterList = filterList;
        this.ties = ties;
    }

    public Join(TableHead tableHead, String alias, List<Filter> filterList, JoinTies ties) {
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
        if (!(o instanceof Join)) {
            return false;
        }
        Join joined = (Join) o;
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

    public static Join fromString(String json) {
        return new Gson().fromJson(json, Join.class);
    }

}
