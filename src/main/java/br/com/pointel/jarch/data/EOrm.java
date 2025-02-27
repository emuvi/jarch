package br.com.pointel.jarch.data;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class EOrm implements Closeable {

    protected final Connection link;

    protected EOrm(Connection link) {
        this.link = link;
    }

    public abstract List<TableHead> getHeads() throws Exception;

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

    @Override
    public void close() throws IOException {
        try {
            this.link.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

}
