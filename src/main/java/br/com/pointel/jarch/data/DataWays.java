package br.com.pointel.jarch.data;

import com.google.gson.Gson;

public class DataWays implements FixVals {

    public DataJdbc dataJdbc;
    public DataLink dataLink;

    public Integer storeMinIdle;
    public Integer storeMaxIdle;
    public Integer storeMaxTotal;

    public DataWays(DataJdbc dataJdbc) {
        this.dataJdbc = dataJdbc;
        this.dataLink = null;
        this.storeMinIdle = 1;
        this.storeMaxIdle = 1;
        this.storeMaxTotal = 1;
    }

    public DataWays(DataLink dataLink) {
        this.dataJdbc = null;
        this.dataLink = dataLink;
        this.storeMinIdle = 1;
        this.storeMaxIdle = 1;
        this.storeMaxTotal = 1;
    }

    public DataWays(DataJdbc dataJdbc,
                    Integer storeMinIdle, Integer storeMaxIdle, Integer storeMaxTotal) {
        this.dataJdbc = dataJdbc;
        this.dataLink = null;
        this.storeMinIdle = storeMinIdle;
        this.storeMaxIdle = storeMaxIdle;
        this.storeMaxTotal = storeMaxTotal;
    }

    public DataWays(DataLink dataLink,
                    Integer storeMinIdle, Integer storeMaxIdle, Integer storeMaxTotal) {
        this.dataJdbc = null;
        this.dataLink = dataLink;
        this.storeMinIdle = storeMinIdle;
        this.storeMaxIdle = storeMaxIdle;
        this.storeMaxTotal = storeMaxTotal;
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
    public void fixNulls() throws Exception {
        if (this.dataJdbc != null) {
            this.dataJdbc.fixNulls();
        }
        if (this.dataLink != null) {
            this.dataLink.fixNulls();
        }
    }

    @Override
    public void fixNullsAndEnvs() throws Exception {
        if (this.dataJdbc != null) {
            this.dataJdbc.fixNullsAndEnvs();
        }
        if (this.dataLink != null) {
            this.dataLink.fixNullsAndEnvs();
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static DataWays fromString(String json) {
        return new Gson().fromJson(json, DataWays.class);
    }

}
