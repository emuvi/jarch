package br.com.pointel.jarch.data;

import java.util.List;
import com.google.gson.Gson;

public class Table implements FixVals {
    public Head registry;
    public List<Field> fields;
    public List<String> keys;

    public Table() {}

    public Table(Head head) {
        this.registry = head;
    }

    public Table(Head head, List<Field> fields) {
        this.registry = head;
        this.fields = fields;
    }

    public Table(Head head, List<Field> fields, List<String> keys) {
        this.registry = head;
        this.fields = fields;
        this.keys = keys;
    }

    public String getSchemaName() {
        return this.registry.getSchemaName();
    }

    public String getCatalogSchemaName() {
        return this.registry.getCatalogSchemaName();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Table fromString(String json) {
        return new Gson().fromJson(json, Table.class);
    }
}
