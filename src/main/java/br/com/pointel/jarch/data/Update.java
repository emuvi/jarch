package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

public class Update implements FixVals {

    public Head head;
    public List<Valued> valuedList;
    public List<Filter> filterList;
    public Integer limit;

    public Update() {}

    public Update(Head head) {
        this.head = head;
    }

    public Update(Head head, List<Valued> valuedList) {
        this.head = head;
        this.valuedList = valuedList;
    }

    public Update(Head head, List<Valued> valuedList, List<Filter> filterList) {
        this.head = head;
        this.valuedList = valuedList;
        this.filterList = filterList;
    }

    public Update(Head head, List<Valued> valuedList, List<Filter> filterList, Integer limit) {
        this.head = head;
        this.valuedList = valuedList;
        this.filterList = filterList;
        this.limit = limit;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Update)) {
            return false;
        }
        Update update = (Update) o;
        return Objects.equals(head, update.head)
                        && Objects.equals(valuedList, update.valuedList)
                        && Objects.equals(filterList, update.filterList)
                        && Objects.equals(limit, update.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, valuedList, filterList, limit);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Update fromString(String json) {
        return new Gson().fromJson(json, Update.class);
    }

}
