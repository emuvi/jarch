package br.com.pointel.jarch.data;

import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class Table implements FixVals {

    public TableHead tableHead;
    public List<Field> fieldList;
    public List<String> keyList;

    public Table() {}

    public Table(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Table(TableHead tableHead, List<Field> fieldList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    public Table(TableHead tableHead, List<Field> fieldList, List<String> keyList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.keyList = keyList;
    }

    public String getReferenceName() {
        return this.tableHead.getReferenceName();
    }

    public String getSchemaName() {
        return this.tableHead.getSchemaName();
    }

    public String getCatalogSchemaName() {
        return this.tableHead.getCatalogSchemaName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Table)) {
            return false;
        }
        Table table = (Table) o;
        return Objects.equals(tableHead, table.tableHead)
                        && Objects.equals(fieldList, table.fieldList)
                        && Objects.equals(keyList, table.keyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHead, fieldList, keyList);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Table fromString(String json) {
        return new Gson().fromJson(json, Table.class);
    }

}
