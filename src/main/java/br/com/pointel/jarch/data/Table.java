package br.com.pointel.jarch.data;

import java.util.List;

public class Table implements Data {

    public TableHead tableHead;
    public List<Field> fieldList;
    public List<KeyPrimary> keyPrimaryList;
    public List<KeyForeign> keyForeignList;

    public Table() {
    }

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
    public boolean equals(Object that) {
        return this.deepEquals(that);
    }

    @Override
    public int hashCode() {
        return this.deepHash();
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static Table fromChars(String chars) {
        return Data.fromChars(chars, Table.class);
    }

}
