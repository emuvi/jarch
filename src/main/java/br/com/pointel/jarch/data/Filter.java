package br.com.pointel.jarch.data;

import java.util.Objects;
import com.google.gson.Gson;

public class Filter implements FixVals {

    public FilterSeems seems;
    public FilterLikes likes;
    public Valued valued;
    public Linked linked;
    public FilterTies joinTie;

    public Filter() {
        this(FilterSeems.SAME, FilterLikes.EQUALS, null, null, FilterTies.AND);
    }

    public Filter(Valued valued) {
        this(FilterSeems.SAME, FilterLikes.EQUALS, valued, null, FilterTies.AND);
    }

    public Filter(Linked linked) {
        this(FilterSeems.SAME, FilterLikes.EQUALS, null, linked, FilterTies.AND);
    }

    public Filter(FilterSeems seem, FilterLikes likes, Valued valued, Linked linked, FilterTies joinTie) {
        this.seems = seem;
        this.likes = likes;
        this.valued = valued;
        this.linked = linked;
        this.joinTie = joinTie;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Filter)) {
            return false;
        }
        Filter filter = (Filter) o;
        return Objects.equals(seems, filter.seems)
                        && Objects.equals(likes, filter.likes)
                        && Objects.equals(valued, filter.valued)
                        && Objects.equals(linked, filter.linked)
                        && Objects.equals(joinTie, filter.joinTie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seems, likes, valued, linked, joinTie);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Filter fromString(String json) {
        return new Gson().fromJson(json, Filter.class);
    }

}
