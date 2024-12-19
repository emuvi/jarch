package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

public class Delete implements FixVals {

    public TableHead tableHead;
    public List<Filter> filterList;

    public Delete() {}

    public Delete(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Delete(TableHead tableHead, List<Filter> filterList) {
        this.tableHead = tableHead;
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
        return Objects.equals(tableHead, delete.tableHead)
                        && Objects.equals(filterList, delete.filterList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHead, filterList);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Delete fromString(String json) {
        return new Gson().fromJson(json, Delete.class);
    }

}
