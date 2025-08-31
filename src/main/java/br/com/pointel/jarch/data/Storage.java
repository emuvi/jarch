package br.com.pointel.jarch.data;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;
import br.com.pointel.jarch.mage.WizChars;

public class Storage {

    private final Map<String, BasicDataSource> stores;
    private final Bases bases;

    public Storage(Bases bases) {
        this.bases = bases;
        this.stores = new HashMap<>();
        this.start();
    }

    public Connection getLink(String ofBaseName) throws Exception {
        if (this.stores == null) {
            throw new Exception("No stores are served.");
        }
        var dataSource = this.stores.get(ofBaseName);
        if (dataSource == null) {
            throw new Exception("Base " + ofBaseName + " not found");
        }
        return dataSource.getConnection();
    }

    public EOrm getEOrm(String onBaseName) throws Exception {
        if (this.stores == null) {
            throw new Exception("No stores are served.");
        }
        var dataSource = this.stores.get(onBaseName);
        if (dataSource == null) {
            throw new Exception("Base " + onBaseName + " not found");
        }
        var link = dataSource.getConnection();
        link.setAutoCommit(true);
        var dataWay = this.bases.getFromName(onBaseName);
        var eOrmClass = dataWay.getEOrmClass();
        return eOrmClass.getConstructor(Connection.class).newInstance(link);
    }

    public ESql getESql(String onBaseName) throws Exception {
        if (this.stores == null) {
            throw new Exception("No stores are served.");
        }
        var dataSource = this.stores.get(onBaseName);
        if (dataSource == null) {
            throw new Exception("Base " + onBaseName + " not found");
        }
        var link = dataSource.getConnection();
        link.setAutoCommit(true);
        return new ESql(link);
    }

    private void start() {
        for (var base : bases) {
            newSourceOnStore(base);
        }
    }

    private void newSourceOnStore(DataWays base) {
        var newSource = new BasicDataSource();
        newSource.setUrl(base.getUrl());
        var user = base.getUser();
        if (!WizChars.isEmpty(user)) {
            newSource.setUsername(user);
        }
        var pass = base.getPass();
        if (!WizChars.isEmpty(pass)) {
            newSource.setPassword(pass);
        }
        newSource.setMinIdle(base.poolMinIdle);
        newSource.setMaxIdle(base.poolMaxIdle);
        newSource.setMaxTotal(base.poolMaxTotal);
        this.stores.put(base.getName(), newSource);
    }

}
