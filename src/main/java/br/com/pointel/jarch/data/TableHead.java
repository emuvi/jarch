package br.com.pointel.jarch.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;
import br.com.pointel.jarch.mage.WizChars;
import br.com.pointel.jarch.mage.WizData;

public class TableHead implements FixVals {

    public String catalog;
    public String schema;
    public String name;
    public String alias;

    public TableHead() {}

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
        return WizChars.sum(".", this.schema, this.name);
    }

    public String getCatalogSchemaName() {
        return WizChars.sum(".", this.catalog, this.schema, this.name);
    }

    public String getNameForFile() {
        return WizChars.sum(".", this.catalog, this.schema, this.name);
    }

    public Table getTable(Connection connection) throws Exception {
        var result = new Table(this, new ArrayList<>(), new ArrayList<>());
        var meta = connection.getMetaData();
        var set = meta.getPrimaryKeys(this.catalog, this.schema, this.name);
        while (set.next()) {
            result.keyList.add(set.getString(4));
        }
        var rst = meta.getColumns(this.catalog, this.schema, this.name, "%");
        while (rst.next()) {
            var field = new Field();
            field.name = rst.getString(4);
            field.nature = WizData.getNatureOfSQL(rst.getInt(5));
            field.size = rst.getInt(7);
            field.precision = rst.getInt(9);
            field.notNull = "NO".equals(rst.getString(18));
            field.key = false;
            if (result.keyList.contains(field.name)) {
                field.key = true;
            }
            field.table = result;
            result.fieldList.add(field);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TableHead)) {
            return false;
        }
        TableHead registry = (TableHead) o;
        return Objects.equals(catalog, registry.catalog)
                        && Objects.equals(schema, registry.schema)
                        && Objects.equals(name, registry.name)
                        && Objects.equals(alias, registry.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalog, schema, name, alias);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static TableHead fromString(String json) {
        return new Gson().fromJson(json, TableHead.class);
    }

}
