package br.com.pointel.jarch.data;

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

    public Filter withField(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }
        this.valued = field.toValued();
        return this;
    }

    public Filter withField(Field field, Object value) {
        if (field == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }
        this.valued = field.toValued(value);
        return this;
    }

    public Filter withValue(Object value) {
        if (this.valued == null) {
            throw new IllegalArgumentException("Valued cannot be null");
        }
        this.valued.value = value;
        return this;
    }

    @Override
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    @Override
    public int hashCode() {
        return this.deepHash();
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Filter fromChars(String chars) {
        return Base.fromChars(chars, Filter.class);
    }

}
