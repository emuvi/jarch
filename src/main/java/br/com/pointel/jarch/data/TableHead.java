package br.com.pointel.jarch.data;

import java.sql.Connection;
import br.com.pointel.jarch.mage.WizBased;
import br.com.pointel.jarch.mage.WizString;

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

    public boolean hasCatalog() {
        return this.catalog != null && !this.catalog.isEmpty();
    }

    public boolean hasSchema() {
        return this.schema != null && !this.schema.isEmpty();
    }

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public boolean hasAlias() {
        return this.alias != null && !this.alias.isEmpty();
    }

    public TableHead withCatalog(String catalog) {
        this.catalog = catalog;
        return this;
    }

    public TableHead withNoCatalog() {
        this.catalog = null;
        return this;
    }

    public TableHead withSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public TableHead withNoSchema() {
        this.schema = null;
        return this;
    }

    public TableHead withName(String name) {
        this.name = name;
        return this;
    }

    public TableHead withNoName() {
        this.name = null;
        return this;
    }

    public TableHead withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public TableHead withNoAlias() {
        this.alias = null;
        return this;
    }

    public TableHead uponCatalog(String catalog) {
        var clone = this.clone();
        clone.catalog = catalog;
        return clone;
    }

    public TableHead uponNoCatalog() {
        var clone = this.clone();
        clone.catalog = null;
        return clone;
    }

    public TableHead uponSchema(String schema) {
        var clone = this.clone();
        clone.schema = schema;
        return clone;
    }

    public TableHead uponNoSchema() {
        var clone = this.clone();
        clone.schema = null;
        return clone;
    }

    public TableHead uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public TableHead uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public TableHead uponAlias(String alias) {
        var clone = this.clone();
        clone.alias = alias;
        return clone;
    }

    public TableHead uponNoAlias() {
        var clone = this.clone();
        clone.alias = null;
        return clone;
    }

    @Override
    public TableHead clone() {
        return (TableHead) this.deepClone();
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
        return Base.fromChars(chars, TableHead.class);
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
            table = WizBased.getTable(this, connection);
        }
        return table;
    }

}
