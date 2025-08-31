package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Objects;
import br.com.pointel.jarch.flow.FixVals;
import br.com.pointel.jarch.mage.WizChars;
import br.com.pointel.jarch.mage.WizInt;

public class DataLink implements Data {
    
    public String name;
    public DataBase base;
    public String path;
    public Integer port;
    public String data;
    public String user;
    public String pass;

    private transient Connection linked = null;

    public DataLink() {}

    public DataLink(String name) {
        this.name = name;
    }

    public DataLink(String name, DataBase base) {
        this.name = name;
        this.base = base;
    }

    public DataLink(String name, DataBase base, String path) {
        this.name = name;
        this.base = base;
        this.path = path;
    }

    public DataLink(String name, DataBase base, String path, String data) {
        this.name = name;
        this.base = base;
        this.path = path;
        this.data = data;
    }

    public DataLink(String name, DataBase base, String path, Integer port, String data) {
        this.name = name;
        this.base = base;
        this.path = path;
        this.port = port;
        this.data = data;
    }

    public DataLink(String name, DataBase base, String path, String data, String user, String pass) {
        this.name = name;
        this.base = base;
        this.path = path;
        this.data = data;
        this.user = user;
        this.pass = pass;
    }

    public DataLink(DataBase base) {
        this.base = base;
    }

    public DataLink(DataBase base, String path) {
        this.base = base;
        this.path = path;
    }

    public DataLink(DataBase base, String path, String data) {
        this.base = base;
        this.path = path;
        this.data = data;
    }

    public DataLink(DataBase base, String path, Integer port, String data) {
        this.base = base;
        this.path = path;
        this.port = port;
        this.data = data;
    }

    public DataLink(DataBase base, String path, String data, String user, String pass) {
        this.base = base;
        this.path = path;
        this.data = data;
        this.user = user;
        this.pass = pass;
    }

    public DataLink(String name, DataBase base, String path, Integer port, String data, String user, String pass) {
        this.name = name;
        this.base = base;
        this.path = path;
        this.port = port;
        this.data = data;
        this.user = user;
        this.pass = pass;
    }

    public String formUrl() {
        var result = this.base.formation;
        if (result.contains("$path") && this.path != null) {
            result = result.replace("$path", this.path);
        }
        if (result.contains("$port")) {
            if (this.port != null) {
                result = result.replace("$port", this.port.toString());
            } else if (this.base != null) {
                result = result.replace("$port", this.base.defaultPort.toString());
            }
        }
        if (result.contains("$data") && this.data != null) {
            result = result.replace("$data", this.data);
        }
        return result;
    }

    public Connection connect() throws Exception {
        Class.forName(this.base.driverClazz);
        if ((this.user != null && !this.user.isEmpty() && this.pass != null)) {
            return DriverManager.getConnection(this.formUrl(), this.user, this.pass);
        }
        return DriverManager.getConnection(this.formUrl());
    }

    public Connection link() throws Exception {
        if (this.linked == null) {
            this.linked = this.connect();
        }
        if (this.linked.isClosed()) {
            this.linked = this.connect();
        }
        return this.linked;
    }

    public EOrm getEOrm(Connection link) throws Exception {
        return this.base.eOrmClazz.getConstructor(Connection.class).newInstance(link);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DataLink)) {
            return false;
        }
        DataLink dataLink = (DataLink) o;
        return Objects.equals(name, dataLink.name) && Objects.equals(base, dataLink.base) && Objects.equals(path, dataLink.path) && Objects.equals(port, dataLink.port) && Objects.equals(data, dataLink.data) && Objects.equals(user, dataLink.user) && Objects.equals(pass, dataLink.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, base, path, port, data, user, pass);
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static DataLink fromChars(String chars) {
        return Data.fromChars(chars, DataLink.class);
    }

    public static DataLink fromAssigned(String inChars) {
        DataLink result = new DataLink();
        Map<String, String> assigned = WizChars.getAssigned(inChars);
        result.name = assigned.get("name");
        result.base = DataBase.fromChars(assigned.get("base"));
        result.path = assigned.get("path");
        result.port = WizInt.fromChars(assigned.get("port"));
        result.data = assigned.get("data");
        result.user = assigned.get("user");
        result.pass = assigned.get("pass");
        return result;
    }
}
