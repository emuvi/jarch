package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class DataJdbc implements FixVals, Serializable {
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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DataJdbc)) {
            return false;
        }
        DataJdbc dataJdbc = (DataJdbc) o;
        return Objects.equals(name, dataJdbc.name)
                        && Objects.equals(url, dataJdbc.url)
                        && Objects.equals(user, dataJdbc.user)
                        && Objects.equals(pass, dataJdbc.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, user, pass);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static DataJdbc fromString(String json) {
        return new Gson().fromJson(json, DataJdbc.class);
    }
}
