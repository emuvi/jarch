package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import br.com.pointel.jarch.flow.FixVals;

public class Index implements Data, FixVals, Serializable {

    public String name;
    public TableHead tableHead;
    public List<Field> fieldList;

    public Index() {
    }

    public Index(String name) {
        this.name = name;
    }

    public Index(TableHead tableHead) {
        this.tableHead = tableHead;
    }

    public Index(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public Index(String name, TableHead tableHead) {
        this.name = name;
        this.tableHead = tableHead;
    }

    public Index(TableHead tableHead, List<Field> fieldList) {
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    public Index(String name, TableHead tableHead, List<Field> fieldList) {
        this.name = name;
        this.tableHead = tableHead;
        this.fieldList = fieldList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Index)) {
            return false;
        }
        Index index = (Index) o;
        return Objects.equals(name, index.name) && Objects.equals(tableHead, index.tableHead) && Objects.equals(fieldList, index.fieldList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tableHead, fieldList);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Index fromChars(String chars) {
        return Data.fromChars(chars, Index.class);
    }

}
