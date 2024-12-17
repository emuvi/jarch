package br.com.pointel.jarch.data;

import com.google.gson.Gson;

public class DataWays implements FixVals {

    public DataJdbc jdbc;
    public DataLink link;
    public Integer storeMinIdle;
    public Integer storeMaxIdle;
    public Integer storeMaxTotal;

    public DataWays(DataJdbc jdbc) {
        this.jdbc = jdbc;
        this.link = null;
        this.storeMinIdle = 1;
        this.storeMaxIdle = 1;
        this.storeMaxTotal = 1;
    }

    public DataWays(DataLink link) {
        this.jdbc = null;
        this.link = link;
        this.storeMinIdle = 1;
        this.storeMaxIdle = 1;
        this.storeMaxTotal = 1;
    }

    public DataWays(DataJdbc jdbc,
                    Integer storeMinIdle, Integer storeMaxIdle, Integer storeMaxTotal) {
        this.jdbc = jdbc;
        this.link = null;
        this.storeMinIdle = storeMinIdle;
        this.storeMaxIdle = storeMaxIdle;
        this.storeMaxTotal = storeMaxTotal;
    }

    public DataWays(DataLink link,
                    Integer storeMinIdle, Integer storeMaxIdle, Integer storeMaxTotal) {
        this.jdbc = null;
        this.link = link;
        this.storeMinIdle = storeMinIdle;
        this.storeMaxIdle = storeMaxIdle;
        this.storeMaxTotal = storeMaxTotal;
    }

    public String getName() {
        if (this.jdbc != null) {
            return this.jdbc.name;
        }
        if (this.link != null) {
            return this.link.name;
        }
        return null;
    }

    public String getUrl() {
        if (this.jdbc != null) {
            return this.jdbc.url;
        }
        if (this.link != null) {
            return this.link.formUrl();
        }
        return null;
    }

    public String getUser() {
        if (this.jdbc != null) {
            return this.jdbc.user;
        }
        if (this.link != null) {
            return this.link.user;
        }
        return null;
    }

    public String getPass() {
        if (this.jdbc != null) {
            return this.jdbc.pass;
        }
        if (this.link != null) {
            return this.link.pass;
        }
        return null;
    }

    public Helper getHelper() {
        if (this.link != null && this.link.base != null) {
            return this.link.base.helper;
        }
        return DataBase.getHelperFromURL(this.getUrl());
    }

    @Override
    public void fixNulls() throws Exception {
        if (this.jdbc != null) {
            this.jdbc.fixNulls();
        }
        if (this.link != null) {
            this.link.fixNulls();
        }
    }

    @Override
    public void fixNullsAndEnvs() throws Exception {
        if (this.jdbc != null) {
            this.jdbc.fixNullsAndEnvs();
        }
        if (this.link != null) {
            this.link.fixNullsAndEnvs();
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
