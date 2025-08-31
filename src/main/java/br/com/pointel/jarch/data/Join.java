package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import br.com.pointel.jarch.flow.FixVals;

public class Join implements Data {

    public TableHead tableHead;
    public String alias;
    public List<Filter> filterList;
    public JoinTies ties;

    public Join() {
    }

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
        Join join = (Join) o;
        return Objects.equals(tableHead, join.tableHead) && Objects.equals(alias, join.alias) && Objects.equals(filterList, join.filterList) && Objects.equals(ties, join.ties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHead, alias, filterList, ties);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Join fromChars(String chars) {
        return Data.fromChars(chars, Join.class);
    }

}
