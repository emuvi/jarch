package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class Update implements FixVals {

    public TableHead tableHead;
    public List<Valued> valuedList;
    public List<Filter> filterList;
    public Integer limit;

    public Update() {}

    public Update(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Update(TableHead tableHead, List<Valued> valuedList) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
    }

    public Update(TableHead tableHead, List<Valued> valuedList, List<Filter> filterList) {
        this.tableHead = tableHead;
        this.valuedList = valuedList;
        this.filterList = filterList;
    }

    public Update(TableHead tableHead, List<Valued> valuedList, List<Filter> filterList, Integer limit) {
        this.tableHead = tableHead;
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
        return Objects.equals(tableHead, update.tableHead)
                        && Objects.equals(valuedList, update.valuedList)
                        && Objects.equals(filterList, update.filterList)
                        && Objects.equals(limit, update.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHead, valuedList, filterList, limit);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Update fromString(String json) {
        return new Gson().fromJson(json, Update.class);
    }

}
