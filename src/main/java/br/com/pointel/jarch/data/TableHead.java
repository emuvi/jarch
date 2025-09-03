package br.com.pointel.jarch.data;

import java.sql.Connection;
import br.com.pointel.jarch.mage.WizString;
import br.com.pointel.jarch.mage.WizData;

public class TableHead implements Data {

    public String catalog;
    public String schema;
    public String name;
    public String alias;

    private transient Table table;

    public TableHead() {
    }

    public TableHead(String name) {
        this.name = name;
    }

    public TableHead(String schema, String name) {
        this.schema = schema;
        this.name = name;
    }

    public TableHead(String catalog, String schema, String name) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
    }

    public TableHead(String catalog, String schema, String name, String alias) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
        this.alias = alias;
    }

    public String getReferenceName() {
        return this.alias != null && !this.alias.isEmpty() ? this.alias : this.getCatalogSchemaName();
    }

    public String getSchemaName() {
        return WizString.sum(".", this.schema, this.name);
    }

    public String getCatalogSchemaName() {
        return WizString.sum(".", this.catalog, this.schema, this.name);
    }

    public String getNameForFile() {
        return WizString.sum(".", this.catalog, this.schema, this.name);
    }

    public Table getTable(Connection connection) throws Exception {
        if (table == null) {
            table = WizData.getTable(this, connection);
        }
        return table;
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

    public static TableHead fromChars(String chars) {
        return Data.fromChars(chars, TableHead.class);
    }

}
