package br.com.pointel.jarch.data;

import br.com.pointel.jarch.flow.FixInt;
import br.com.pointel.jarch.flow.NotFixNulls;

public class BasedWays implements Data {
    
    @NotFixNulls
    public BasedJdbc dataJdbc;
    @NotFixNulls
    public BasedLink dataLink;

    @FixInt(1) 
    public Integer poolMinIdle;
    @FixInt(5)
    public Integer poolMaxIdle;
    @FixInt(10)
    public Integer poolMaxTotal;

    public BasedWays() {
    }

    public BasedWays(BasedJdbc dataJdbc) {
        this.dataJdbc = dataJdbc;
        this.poolMinIdle = 2;
        this.poolMaxIdle = 5;
        this.poolMaxTotal = 10;
    }

    public BasedWays(BasedLink dataLink) {
        this.dataJdbc = null;
        this.dataLink = dataLink;
        this.poolMinIdle = 2;
        this.poolMaxIdle = 5;
        this.poolMaxTotal = 10;
    }

    public BasedWays(BasedJdbc dataJdbc, Integer poolMinIdle, Integer poolMaxIdle, Integer poolMaxTotal) {
        this.dataJdbc = dataJdbc;
        this.dataLink = null;
        this.poolMinIdle = poolMinIdle;
        this.poolMaxIdle = poolMaxIdle;
        this.poolMaxTotal = poolMaxTotal;
    }

    public BasedWays(BasedLink dataLink, Integer poolMinIdle, Integer poolMaxIdle, Integer poolMaxTotal) {
        this.dataJdbc = null;
        this.dataLink = dataLink;
        this.poolMinIdle = poolMinIdle;
        this.poolMaxIdle = poolMaxIdle;
        this.poolMaxTotal = poolMaxTotal;
    }

    public boolean hasDataJdbc() {
        return this.dataJdbc != null;
    }

    public boolean hasDataLink() {
        return this.dataLink != null;
    }

    public boolean hasPoolMinIdle() {
        return this.poolMinIdle != null;
    }

    public boolean hasPoolMaxIdle() {
        return this.poolMaxIdle != null;
    }

    public boolean hasPoolMaxTotal() {
        return this.poolMaxTotal != null;
    }

    public BasedWays withDataJdbc(BasedJdbc dataJdbc) {
        this.dataJdbc = dataJdbc;
        return this;
    }

    public BasedWays withNoDataJdbc() {
        this.dataJdbc = null;
        return this;
    }

    public BasedWays withDataLink(BasedLink dataLink) {
        this.dataLink = dataLink;
        return this;
    }

    public BasedWays withNoDataLink() {
        this.dataLink = null;
        return this;
    }

    public BasedWays withPoolMinIdle(Integer poolMinIdle) {
        this.poolMinIdle = poolMinIdle;
        return this;
    }

    public BasedWays withNoPoolMinIdle() {
        this.poolMinIdle = null;
        return this;
    }

    public BasedWays withPoolMaxIdle(Integer poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
        return this;
    }

    public BasedWays withNoPoolMaxIdle() {
        this.poolMaxIdle = null;
        return this;
    }

    public BasedWays withPoolMaxTotal(Integer poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
        return this;
    }

    public BasedWays withNoPoolMaxTotal() {
        this.poolMaxTotal = null;
        return this;
    }

    public BasedWays uponDataJdbc(BasedJdbc dataJdbc) {
        var clone = this.clone();
        clone.dataJdbc = dataJdbc;
        return clone;
    }

    public BasedWays uponNoDataJdbc() {
        var clone = this.clone();
        clone.dataJdbc = null;
        return clone;
    }

    public BasedWays uponDataLink(BasedLink dataLink) {
        var clone = this.clone();
        clone.dataLink = dataLink;
        return clone;
    }

    public BasedWays uponNoDataLink() {
        var clone = this.clone();
        clone.dataLink = null;
        return clone;
    }

    public BasedWays uponPoolMinIdle(Integer poolMinIdle) {
        var clone = this.clone();
        clone.poolMinIdle = poolMinIdle;
        return clone;
    }

    public BasedWays uponNoPoolMinIdle() {
        var clone = this.clone();
        clone.poolMinIdle = null;
        return clone;
    }

    public BasedWays uponPoolMaxIdle(Integer poolMaxIdle) {
        var clone = this.clone();
        clone.poolMaxIdle = poolMaxIdle;
        return clone;
    }

    public BasedWays uponNoPoolMaxIdle() {
        var clone = this.clone();
        clone.poolMaxIdle = null;
        return clone;
    }

    public BasedWays uponPoolMaxTotal(Integer poolMaxTotal) {
        var clone = this.clone();
        clone.poolMaxTotal = poolMaxTotal;
        return clone;
    }

    public BasedWays uponNoPoolMaxTotal() {
        var clone = this.clone();
        clone.poolMaxTotal = null;
        return clone;
    }

    @Override
    public BasedWays clone() {
        return (BasedWays) this.deepClone();
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

    public static BasedWays fromChars(String chars) {
        return Base.fromChars(chars, BasedWays.class);
    }

    public String getName() {
        if (this.dataJdbc != null) {
            return this.dataJdbc.name;
        }
        if (this.dataLink != null) {
            return this.dataLink.name;
        }
        return null;
    }

    public String getUrl() {
        if (this.dataJdbc != null) {
            return this.dataJdbc.url;
        }
        if (this.dataLink != null) {
            return this.dataLink.formUrl();
        }
        return null;
    }

    public String getUser() {
        if (this.dataJdbc != null) {
            return this.dataJdbc.user;
        }
        if (this.dataLink != null) {
            return this.dataLink.user;
        }
        return null;
    }

    public String getPass() {
        if (this.dataJdbc != null) {
            return this.dataJdbc.pass;
        }
        if (this.dataLink != null) {
            return this.dataLink.pass;
        }
        return null;
    }

    public Class<? extends EOrm> getEOrmClass() {
        if (this.dataLink != null && this.dataLink.base != null) {
            return this.dataLink.base.eOrmClazz;
        }
        return Based.getEOrmClassFromURL(this.getUrl());
    }

}
