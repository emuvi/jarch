package br.com.pointel.jarch.data;

import com.google.gson.Gson;

public class Filter implements FixVals {
    
    public FilterSeems seems;
    public FilterLikes likes;
    public Valued valued;
    public Linked linked;
    public FilterTies ties;

    public Filter() {
        this(FilterSeems.SAME, FilterLikes.EQUALS, null, null, FilterTies.AND);
    }

    public Filter(Valued valued) {
        this(FilterSeems.SAME, FilterLikes.EQUALS, valued, null, FilterTies.AND);
    }

    public Filter(Linked linked) {
        this(FilterSeems.SAME, FilterLikes.EQUALS, null, linked, FilterTies.AND);
    }

    public Filter(FilterSeems seem, FilterLikes likes, Valued valued, Linked linked, FilterTies ties) {
        this.seems = seem;
        this.likes = likes;
        this.valued = valued;
        this.linked = linked;
        this.ties = ties;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Filter fromString(String json) {
        return new Gson().fromJson(json, Filter.class);
    }

}
