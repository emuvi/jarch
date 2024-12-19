package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

public class Insert implements FixVals {

    public Head head;
    public List<Valued> valuedList;
    public ToGetID toGetID;

    public Insert() {}

    public Insert(Head head) {
        this.head = head;
    }

    public Insert(Head head, List<Valued> valuedList) {
        this.head = head;
        this.valuedList = valuedList;
    }

    public Insert(Head head, List<Valued> valuedList, ToGetID toGetID) {
        this.head = head;
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
        return Objects.equals(head, insert.head)
                        && Objects.equals(valuedList, insert.valuedList)
                        && Objects.equals(toGetID, insert.toGetID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, valuedList, toGetID);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Insert fromString(String json) {
        return new Gson().fromJson(json, Insert.class);
    }

}
