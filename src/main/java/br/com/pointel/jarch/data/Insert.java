package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import br.com.pointel.jarch.flow.FixVals;

public class Insert implements Data {

    public TableHead tableHead;
    public List<Valued> valuedList;
    public ToGetID toGetID;

    public Insert() {
    }

    public Insert(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Insert(TableHead tableHead, List<Valued> valuedList) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
    }

    public Insert(TableHead tableHead, List<Valued> valuedList, ToGetID toGetID) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
        this.toGetID = toGetID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Insert)) {
            return false;
        }
        Insert insert = (Insert) o;
        return Objects.equals(tableHead, insert.tableHead) && Objects.equals(valuedList, insert.valuedList) && Objects.equals(toGetID, insert.toGetID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHead, valuedList, toGetID);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Insert fromChars(String chars) {
        return Data.fromChars(chars, Insert.class);
    }

}
