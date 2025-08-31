package br.com.pointel.jarch.data;

import java.util.Objects;

public class Filter implements Data {

    public FilterSeems seems;
    public FilterLikes likes;
    public Valued valued;
    public Linked linked;
    public FilterTies ties;

    public Filter() {
        this(FilterSeems.Is, FilterLikes.Equals, null, null, FilterTies.And);
    }

    public Filter(Valued valued) {
        this(FilterSeems.Is, FilterLikes.Equals, valued, null, FilterTies.And);
    }

    public Filter(Linked linked) {
        this(FilterSeems.Is, FilterLikes.Equals, null, linked, FilterTies.And);
    }

    public Filter(FilterSeems seem, FilterLikes likes, Valued valued, Linked linked, FilterTies ties) {
        this.seems = seem;
        this.likes = likes;
        this.valued = valued;
        this.linked = linked;
        this.ties = ties;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Filter)) {
            return false;
        }
        Filter filter = (Filter) o;
        return Objects.equals(seems, filter.seems) && Objects.equals(likes, filter.likes) && Objects.equals(valued, filter.valued) && Objects.equals(linked, filter.linked) && Objects.equals(ties, filter.ties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seems, likes, valued, linked, ties);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Filter fromChars(String chars) {
        return Data.fromChars(chars, Filter.class);
    }

}
