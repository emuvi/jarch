package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

public class Delete implements FixVals {

    public Head head;
    public List<Filter> filterList;

    public Delete() {}

    public Delete(Head head) {
        this.head = head;
    }

    public Delete(Head head, List<Filter> filterList) {
        this.head = head;
        this.filterList = filterList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Delete)) {
            return false;
        }
        Delete delete = (Delete) o;
        return Objects.equals(head, delete.head)
                        && Objects.equals(filterList, delete.filterList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, filterList);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Delete fromString(String json) {
        return new Gson().fromJson(json, Delete.class);
    }

}
