package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

public class Joined implements FixVals {

    public Head head;
    public String alias;
    public List<Filter> filterList;
    public JoinedTies ties;

    public Joined() {}

    public Joined(JoinedTies ties) {
        this.ties = ties;
    }

    public Joined(Head head) {
        this.head = head;
    }

    public Joined(Head head, String alias) {
        this.head = head;
        this.alias = alias;
    }

    public Joined(Head head, JoinedTies ties) {
        this.head = head;
        this.ties = ties;
    }

    public Joined(Head head, String alias, JoinedTies ties) {
        this.head = head;
        this.alias = alias;
        this.ties = ties;
    }

    public Joined(Head head, List<Filter> filterList) {
        this.head = head;
        this.filterList = filterList;
    }

    public Joined(Head head, String alias, List<Filter> filterList) {
        this.head = head;
        this.alias = alias;
        this.filterList = filterList;
    }

    public Joined(Head head, List<Filter> filterList, JoinedTies ties) {
        this.head = head;
        this.filterList = filterList;
        this.ties = ties;
    }

    public Joined(Head head, String alias, List<Filter> filterList, JoinedTies ties) {
        this.head = head;
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
        return Objects.equals(head, joined.head)
                        && Objects.equals(alias, joined.alias)
                        && Objects.equals(filterList, joined.filterList)
                        && Objects.equals(ties, joined.ties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, alias, filterList, ties);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Joined fromString(String json) {
        return new Gson().fromJson(json, Joined.class);
    }

}
