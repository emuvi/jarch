package br.com.pointel.jarch.data;

public class BasedJdbc implements Data {
    
    public String name;
    public String url;
    public String user;
    public String pass;

    public BasedJdbc() {}

    public BasedJdbc(String url) {
        this.url = url;
    }

    public BasedJdbc(String url, String user) {
        this.url = url;
        this.user = user;
    }

    public BasedJdbc(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public BasedJdbc(String name, String url, String user, String pass) {
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

    public static BasedJdbc fromChars(String chars) {
        return Base.fromChars(chars, BasedJdbc.class);
    }
}
