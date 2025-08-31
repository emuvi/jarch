package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import br.com.pointel.jarch.flow.FixVals;

public class Delete implements Data, FixVals, Serializable {

    public TableHead tableHead;
    public List<Filter> filterList;

    public Delete() {
    }

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
        return Objects.equals(tableHead, delete.tableHead) && Objects.equals(filterList, delete.filterList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHead, filterList);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Delete fromChars(String chars) {
        return Data.fromChars(chars, Delete.class);
    }

}
