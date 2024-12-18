package br.com.pointel.jarch.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public abstract class EOrm {

    protected final Connection link;

    protected EOrm(Connection link) {
        this.link = link;
    }

    public abstract List<Head> getHeads() throws Exception;

    public abstract void create(Table table) throws Exception;

    public abstract void create(Table table, boolean ifNotExists) throws Exception;

    public abstract ResultSet select(Select select) throws Exception;

    public abstract ResultSet select(Select select, Strain strain) throws Exception;

    public abstract String insert(Insert insert) throws Exception;

    public abstract String insert(Insert insert, Strain strain) throws Exception;

    public abstract Integer update(Update update) throws Exception;

    public abstract Integer update(Update update, Strain strain) throws Exception;

    public abstract Integer delete(Delete delete) throws Exception;

    public abstract Integer delete(Delete delete, Strain strain) throws Exception;

    public abstract boolean isPrimaryKeyError(Exception error);

}
