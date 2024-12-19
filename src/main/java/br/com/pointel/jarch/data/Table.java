package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

public class Table implements FixVals {

    public Head head;
    public List<Field> fieldList;
    public List<String> keyList;

    public Table() {}

    public Table(Head head) {
        this.head = head;
    }

    public Table(Head head, List<Field> fieldList) {
        this.head = head;
        this.fieldList = fieldList;
    }

    public Table(Head head, List<Field> fieldList, List<String> keyList) {
        this.head = head;
        this.fieldList = fieldList;
        this.keyList = keyList;
    }

    public String getSchemaName() {
        return this.head.getSchemaName();
    }

    public String getCatalogSchemaName() {
        return this.head.getCatalogSchemaName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Table)) {
            return false;
        }
        Table table = (Table) o;
        return Objects.equals(head, table.head)
                        && Objects.equals(fieldList, table.fieldList)
                        && Objects.equals(keyList, table.keyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, fieldList, keyList);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Table fromString(String json) {
        return new Gson().fromJson(json, Table.class);
    }

}
