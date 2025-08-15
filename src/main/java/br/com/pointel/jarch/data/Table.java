package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;
import java.util.Objects;

public class Table {

    public TableHead tableHead;
    public List<Field> fieldList;
    public List<KeyPrimary> keyPrimaryList;
    public List<KeyForeign> keyForeignList;

    public Table() {}

    public Table(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Table(TableHead tableHead, List<Field> fieldList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    public Table(TableHead tableHead, List<Field> fieldList, List<KeyPrimary> keyPrimaryList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.keyPrimaryList = keyPrimaryList;
    }

    public Table(TableHead tableHead, List<Field> fieldList, List<KeyPrimary> keyPrimaryList, List<KeyForeign> keyForeignList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
        this.keyPrimaryList = keyPrimaryList;
        this.keyForeignList = keyForeignList;
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
        return Objects.equals(tableHead, table.tableHead) && Objects.equals(fieldList, table.fieldList) && Objects.equals(keyPrimaryList, table.keyPrimaryList) && Objects.equals(keyForeignList, table.keyForeignList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableHead, fieldList, keyPrimaryList, keyForeignList);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Table fromString(String json) {
        return new Gson().fromJson(json, Table.class);
    }

}
