package br.com.pointel.jarch.data;

public class BasedJdbc implements Data {

    public String name;
    public String url;
    public String user;
    public String pass;

    public BasedJdbc() {
    }

    public BasedJdbc(String name) {
        this.name = name;
    }

    public BasedJdbc(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public BasedJdbc(String name, String url, String user) {
        this.name = name;
        this.url = url;
        this.user = user;
    }

    public BasedJdbc(String name, String url, String user, String pass) {
        this.name = name;
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public boolean hasName() {
        return this.name != null && !this.name.isEmpty();
    }

    public boolean hasUrl() {
        return this.url != null && !this.url.isEmpty();
    }

    public boolean hasUser() {
        return this.user != null && !this.user.isEmpty();
    }

    public boolean hasPass() {
        return this.pass != null && !this.pass.isEmpty();
    }

    public BasedJdbc withName(String name) {
        this.name = name;
        return this;
    }

    public BasedJdbc withNoName() {
        this.name = null;
        return this;
    }

    public BasedJdbc withUrl(String url) {
        this.url = url;
        return this;
    }

    public BasedJdbc withNoUrl() {
        this.url = null;
        return this;
    }

    public BasedJdbc withUser(String user) {
        this.user = user;
        return this;
    }

    public BasedJdbc withNoUser() {
        this.user = null;
        return this;
    }

    public BasedJdbc withPass(String pass) {
        this.pass = pass;
        return this;
    }

    public BasedJdbc withNoPass() {
        this.pass = null;
        return this;
    }

    public BasedJdbc uponName(String name) {
        var clone = this.clone();
        clone.name = name;
        return clone;
    }

    public BasedJdbc uponNoName() {
        var clone = this.clone();
        clone.name = null;
        return clone;
    }

    public BasedJdbc uponUrl(String url) {
        var clone = this.clone();
        clone.url = url;
        return clone;
    }

    public BasedJdbc uponNoUrl() {
        var clone = this.clone();
        clone.url = null;
        return clone;
    }

    public BasedJdbc uponUser(String user) {
        var clone = this.clone();
        clone.user = user;
        return clone;
    }

    public BasedJdbc uponNoUser() {
        var clone = this.clone();
        clone.user = null;
        return clone;
    }

    public BasedJdbc uponPass(String pass) {
        var clone = this.clone();
        clone.pass = pass;
        return clone;
    }

    public BasedJdbc uponNoPass() {
        var clone = this.clone();
        clone.pass = null;
        return clone;
    }

    @Override
    public BasedJdbc clone() {
        return (BasedJdbc) this.deepClone();
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

    public static BasedJdbc fromChars(String chars) {
        return Base.fromChars(chars, BasedJdbc.class);
    }
}
