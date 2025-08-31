package br.com.pointel.jarch.data;

import java.util.Objects;

public class DataJdbc implements Data {
    
    public String name;
    public String url;
    public String user;
    public String pass;

    public DataJdbc() {}

    public DataJdbc(String url) {
        this.url = url;
    }

    public DataJdbc(String url, String user) {
        this.url = url;
        this.user = user;
    }

    public DataJdbc(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public DataJdbc(String name, String url, String user, String pass) {
        this.name = name;
        this.url = url;
        this.user = user;
        this.pass = pass;
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

    public static DataJdbc fromChars(String chars) {
        return Data.fromChars(chars, DataJdbc.class);
    }
}
