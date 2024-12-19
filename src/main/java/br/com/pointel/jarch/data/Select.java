package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

public class Select implements FixVals {

    public Head head;
    public List<Typed> fieldList;
    public List<Joined> joinList;
    public List<Filter> filterList;
    public List<Order> orderList;
    public Integer offset;
    public Integer limit;

    public Select() {}

    public Select(Head head) {
        this.head = head;
    }

    public Select(Head head, List<Typed> fieldList) {
        this.head = head;
        this.fieldList = fieldList;
    }

    public Select(Head head, List<Typed> fieldList, List<Joined> joinList) {
        this.head = head;
        this.fieldList = fieldList;
        this.joinList = joinList;
    }

    public Select(Head head, List<Typed> fieldList, List<Joined> joinList, List<Filter> filterList) {
        this.head = head;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
    }

    public Select(Head head, List<Typed> fieldList, List<Joined> joinList, List<Filter> filterList,
                    List<Order> orderList) {
        this.head = head;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
        this.orderList = orderList;
    }

    public Select(Head head, List<Typed> fieldList, List<Joined> joinList, List<Filter> filterList,
                    List<Order> orderList, Integer offset) {
        this.head = head;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
        this.orderList = orderList;
        this.offset = offset;
    }

    public Select(Head head, List<Typed> fieldList, List<Joined> joinList, List<Filter> filterList,
                    List<Order> orderList, Integer offset, Integer limit) {
        this.head = head;
        this.fieldList = fieldList;
        this.joinList = joinList;
        this.filterList = filterList;
        this.orderList = orderList;
        this.offset = offset;
        this.limit = limit;
    }

    public boolean hasJoins() {
        return this.joinList != null && !this.joinList.isEmpty();
    }

    public boolean hasFilters() {
        return this.filterList != null && !this.filterList.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Select)) {
            return false;
        }
        Select select = (Select) o;
        return Objects.equals(head, select.head)
                        && Objects.equals(fieldList, select.fieldList)
                        && Objects.equals(joinList, select.joinList)
                        && Objects.equals(filterList, select.filterList)
                        && Objects.equals(orderList, select.orderList)
                        && Objects.equals(offset, select.offset)
                        && Objects.equals(limit, select.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, fieldList, joinList, filterList, orderList, offset, limit);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Select fromString(String json) {
        return new Gson().fromJson(json, Select.class);
    }

}
