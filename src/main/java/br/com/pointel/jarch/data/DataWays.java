package br.com.pointel.jarch.data;

import java.io.Serializable;
import java.util.Objects;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixVals;

public class DataWays implements FixVals, Serializable {
    
    public DataJdbc dataJdbc;
    public DataLink dataLink;
    public Integer poolMinIdle;
    public Integer poolMaxIdle;
    public Integer poolMaxTotal;

    public DataWays(DataJdbc dataJdbc) {
        this.dataJdbc = dataJdbc;
        this.dataLink = null;
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
    public void fixNulls() throws Exception {
        if (this.dataJdbc != null) {
            this.dataJdbc.fixNulls();
        }
        if (this.dataLink != null) {
            this.dataLink.fixNulls();
        }
        if (this.poolMinIdle == null) {
            this.poolMinIdle = 1;
        }
        if (this.poolMaxIdle == null) {
            this.poolMaxIdle = 1;
        }
        if (this.poolMaxTotal == null) {
            this.poolMaxTotal = 1;
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
        if (this.poolMinIdle == null) {
            this.poolMinIdle = 1;
        }
        if (this.poolMaxIdle == null) {
            this.poolMaxIdle = 1;
        }
        if (this.poolMaxTotal == null) {
            this.poolMaxTotal = 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DataWays)) {
            return false;
        }
        DataWays dataWays = (DataWays) o;
        return Objects.equals(dataJdbc, dataWays.dataJdbc)
                        && Objects.equals(dataLink, dataWays.dataLink)
                        && Objects.equals(poolMinIdle, dataWays.poolMinIdle)
                        && Objects.equals(poolMaxIdle, dataWays.poolMaxIdle)
                        && Objects.equals(poolMaxTotal, dataWays.poolMaxTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataJdbc, dataLink, poolMinIdle, poolMaxIdle, poolMaxTotal);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static DataWays fromString(String json) {
        return new Gson().fromJson(json, DataWays.class);
    }

}
