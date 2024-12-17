package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;

public class Joined {

    public Head head;
    public String alias;
    public List<Filter> filters;
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

    public Joined(Head head, List<Filter> filters) {
        this.head = head;
        this.filters = filters;
    }

    public Joined(Head head, String alias, List<Filter> filters) {
        this.head = head;
        this.alias = alias;
        this.filters = filters;
    }

    public Joined(Head head, List<Filter> filters, JoinedTies ties) {
        this.head = head;
        this.filters = filters;
        this.ties = ties;
    }

    public Joined(Head head, String alias, List<Filter> filters, JoinedTies ties) {
        this.head = head;
        this.alias = alias;
        this.filters = filters;
        this.ties = ties;
    }

    public boolean hasFilters() {
        return this.filters != null && !this.filters.isEmpty();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Joined fromString(String json) {
        return new Gson().fromJson(json, Joined.class);
    }

}
