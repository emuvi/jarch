package br.com.pointel.jarch.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import br.com.pointel.jarch.mage.WizString;
import br.com.pointel.jarch.mage.WizData;
import br.com.pointel.jarch.mage.WizInteger;

public class BasedLink implements Data {
    
    public String name;
    public Based base;
    public String path;
    public Integer port;
    public String data;
    public String user;
    public String pass;

    private transient Connection linked = null;

    public BasedLink() {
    }

    public BasedLink(String name) {
        this.name = name;
    }

    public BasedLink(String name, Based base) {
        this.name = name;
        this.base = base;
    }

    public BasedLink(String name, Based base, String path) {
        this.name = name;
        this.base = base;
        this.path = path;
    }

    public BasedLink(String name, Based base, String path, Integer port) {
        this.name = name;
        this.base = base;
        this.path = path;
        this.port = port;
    }

    public BasedLink(String name, Based base, String path, Integer port, String data) {
        this.name = name;
        this.base = base;
        this.path = path;
        this.port = port;
        this.data = data;
    }

    public BasedLink(String name, Based base, String path, Integer port, String data, String user, String pass) {
        this.name = name;
        this.base = base;
        this.path = path;
        this.port = port;
        this.data = data;
        this.user = user;
        this.pass = pass;
    }

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public boolean hasBase() {
        return this.base != null;
    }

    public boolean hasPath() {
        return this.path != null && !this.path.isEmpty();
    }

    public boolean hasPort() {
        return this.port != null;
    }

    public boolean hasData() {
        return this.data != null && !this.data.isEmpty();
    }

    public boolean hasUser() {
        return this.user != null && !this.user.isEmpty();
    }

    public boolean hasPass() {
        return this.pass != null && !this.pass.isEmpty();
    }

    public BasedLink withName(String name) {
        this.name = name;
        return this;
    }

    public BasedLink withNoName() {
        this.name = null;
        return this;
    }

    public BasedLink withBase(Based base) {
        this.base = base;
        return this;
    }

    public BasedLink withNoBase() {
        this.base = null;
        return this;
    }

    public BasedLink withPath(String path) {
        this.path = path;
        return this;
    }

    public BasedLink withNoPath() {
        this.path = null;
        return this;
    }

    public BasedLink withPort(Integer port) {
        this.port = port;
        return this;
    }

    public BasedLink withNoPort() {
        this.port = null;
        return this;
    }

    public BasedLink withData(String data) {
        this.data = data;
        return this;
    }

    public BasedLink withNoData() {
        this.data = null;
        return this;
    }

    public BasedLink withUser(String user) {
        this.user = user;
        return this;
    }

    public BasedLink withNoUser() {
        this.user = null;
        return this;
    }

    public BasedLink withPass(String pass) {
        this.pass = pass;
        return this;
    }

    public BasedLink withNoPass() {
        this.pass = null;
        return this;
    }

    public BasedLink uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public BasedLink uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public BasedLink uponBase(Based base) {
        var clone = this.clone();
        clone.base = base;
        return clone;
    }

    public BasedLink uponNoBase() {
        var clone = this.clone();
        clone.base = null;
        return clone;
    }

    public BasedLink uponPath(String path) {
        var clone = this.clone();
        clone.path = path;
        return clone;
    }

    public BasedLink uponNoPath() {
        var clone = this.clone();
        clone.path = null;
        return clone;
    }

    public BasedLink uponPort(Integer port) {
        var clone = this.clone();
        clone.port = port;
        return clone;
    }

    public BasedLink uponNoPort() {
        var clone = this.clone();
        clone.port = null;
        return clone;
    }

    public BasedLink uponData(String data) {
        var clone = this.clone();
        clone.data = data;
        return clone;
    }

    public BasedLink uponNoData() {
        var clone = this.clone();
        clone.data = null;
        return clone;
    }

    public BasedLink uponUser(String user) {
        var clone = this.clone();
        clone.user = user;
        return clone;
    }

    public BasedLink uponNoUser() {
        var clone = this.clone();
        clone.user = null;
        return clone;
    }

    public BasedLink uponPass(String pass) {
        var clone = this.clone();
        clone.pass = pass;
        return clone;
    }

    public BasedLink uponNoPass() {
        var clone = this.clone();
        clone.pass = null;
        return clone;
    }

    @Override
    public BasedLink clone() {
        return (BasedLink) this.deepClone();
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

    public static BasedLink fromChars(String chars) {
        return Base.fromChars(chars, BasedLink.class);
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

    public static BasedLink fromAssigned(String chars) throws Exception {
        BasedLink result = new BasedLink();
        Map<String, String> assigned = WizString.getAssigned(chars);
        result.name = assigned.get("name");
        result.base = WizData.fromJson(assigned.get("base"), Based.class);
        result.path = assigned.get("path");
        result.port = WizInteger.get(assigned.get("port"));
        result.data = assigned.get("data");
        result.user = assigned.get("user");
        result.pass = assigned.get("pass");
        return result;
    }
}
