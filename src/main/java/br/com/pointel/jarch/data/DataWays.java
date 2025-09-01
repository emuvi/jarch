package br.com.pointel.jarch.data;

import br.com.pointel.jarch.flow.FixInt;
import br.com.pointel.jarch.flow.NotFixNulls;
import br.com.pointel.jarch.mage.WizChars;

public class DataWays implements Data {
    
    @NotFixNulls
    public DataJdbc dataJdbc;
    @NotFixNulls
    public DataLink dataLink;

    @FixInt(1) 
    public Integer poolMinIdle;
    @FixInt(5)
    public Integer poolMaxIdle;
    @FixInt(10)
    public Integer poolMaxTotal;

    public DataWays() {
    }

    public DataWays(DataJdbc dataJdbc) {
        this.dataJdbc = dataJdbc;
        this.poolMinIdle = 1;
        this.poolMaxIdle = 1;
        this.poolMaxTotal = 1;
    }

    public DataWays(DataLink dataLink) {
        this.dataJdbc = null;
        this.dataLink = dataLink;
        this.poolMinIdle = 1;
        this.poolMaxIdle = 1;
        this.poolMaxTotal = 1;
    }

    public DataWays(DataJdbc dataJdbc, Integer poolMinIdle, Integer poolMaxIdle, Integer poolMaxTotal) {
        this.dataJdbc = dataJdbc;
        this.dataLink = null;
        this.poolMinIdle = poolMinIdle;
        this.poolMaxIdle = poolMaxIdle;
        this.poolMaxTotal = poolMaxTotal;
    }

    public DataWays(DataLink dataLink, Integer poolMinIdle, Integer poolMaxIdle, Integer poolMaxTotal) {
        this.dataJdbc = null;
        this.dataLink = dataLink;
        this.poolMinIdle = poolMinIdle;
        this.poolMaxIdle = poolMaxIdle;
        this.poolMaxTotal = poolMaxTotal;
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
        return DataBase.getEOrmClassFromURL(this.getUrl());
    }

    @Override
    public void fixDefaults() throws Exception {
        if (this.dataJdbc != null && WizChars.isEmpty(this.dataJdbc.name)) {
            this.dataJdbc = null;
        }
        if (this.dataLink != null && WizChars.isEmpty(this.dataLink.name)) {
            this.dataLink = null;
        }
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

    public static DataWays fromChars(String chars) {
        return Data.fromChars(chars, DataWays.class);
    }

}
