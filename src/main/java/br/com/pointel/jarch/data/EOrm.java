package br.com.pointel.jarch.data;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class EOrm implements Closeable {

    private final Connection link;

    protected EOrm(Connection link) {
        this.link = link;
    }

    public Connection getLink() {
        return link;
    }

    public abstract Heads getHeads() throws Exception;

    public abstract void create(Table table, boolean ifNotExists) throws Exception;
    
    public void create(Table table) throws Exception {
        create(table, false);
    }

    public void createIfNotExists(Table table) throws Exception {
        create(table, true);
    }

    public abstract void create(Index index, boolean ifNotExists) throws Exception;

    public void create(Index index) throws Exception {
        create(index, false);
    }

    public void createIfNotExists(Index index) throws Exception {
        create(index, true);
    }

    public abstract ResultSet select(Select select, Strain strain) throws Exception;

    public ResultSet select(Select select) throws Exception {
        return select(select, null);
    }

    public abstract String insert(Insert insert, Strain strain) throws Exception;

    public String insert(Insert insert) throws Exception {
        return insert(insert, null);
    }

    public abstract Integer update(Update update, Strain strain) throws Exception;

    public Integer update(Update update) throws Exception {
        return update(update, null);
    }

    public abstract Integer delete(Delete delete, Strain strain) throws Exception;

    public Integer delete(Delete delete) throws Exception {
        return delete(delete, null);
    }

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
