package br.com.pointel.jarch.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.dbcp2.BasicDataSource;
import br.com.pointel.jarch.mage.WizChars;

public class Storage {

    private final Map<String, BasicDataSource> stores;
    private volatile Bases bases;

    public Storage(Bases bases) {
        this.bases = bases;
        this.stores = new HashMap<>();
        this.start(bases);
    }

    public synchronized void update(Bases bases) throws SQLException {
        var included = new ArrayList<String>();
        for (var base : bases) {
            if (this.stores.containsKey(base.getName())) {
                var oldSource = stores.get(base.getName());
                if (!Objects.equals(oldSource.getUrl(), base.getUrl())
                        || !Objects.equals(oldSource.getUserName(), base.getUser())
                        || !Objects.equals(oldSource.getPassword(), base.getPass())) {
                    oldSource.close();
                    newSourceOnStore(base);
                }
            } else {
                newSourceOnStore(base);
            }
            included.add(base.getName());
        }
        for (var baseName : this.stores.keySet()) {
            if (!included.contains(baseName)) {
                this.stores.get(baseName).close();
                this.stores.remove(baseName);
            }
        }
        this.bases = bases;
    }

    public synchronized Connection getLink(String ofBaseName) throws Exception {
        if (this.stores == null) {
            throw new Exception("No stores are served.");
        }
        var stored = this.stores.get(ofBaseName);
        if (stored == null) {
            throw new Exception("Base " + ofBaseName + " not found");
        }
        return stored.getConnection();
    }

    public synchronized EOrm getEOrm(String onBaseName) throws Exception {
        if (this.stores == null) {
            throw new Exception("No stores are served.");
        }
        var stored = this.stores.get(onBaseName);
        if (stored == null) {
            throw new Exception("Base " + onBaseName + " not found");
        }
        var link = stored.getConnection();
        link.setAutoCommit(true);
        var dataWay = this.bases.getFromName(onBaseName);
        var eOrmClass = dataWay.getEOrmClass();
        return eOrmClass.getConstructor(Connection.class).newInstance(link);
    }

    public synchronized ESql getESql(String onBaseName) throws Exception {
        if (this.stores == null) {
            throw new Exception("No stores are served.");
        }
        var stored = this.stores.get(onBaseName);
        if (stored == null) {
            throw new Exception("Base " + onBaseName + " not found");
        }
        var link = stored.getConnection();
        link.setAutoCommit(true);
        return new ESql(link);
    }

    private void start(Bases bases) {
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
        newSource.setMinIdle(base.storeMinIdle);
        newSource.setMaxIdle(base.storeMaxIdle);
        newSource.setMaxTotal(base.storeMaxTotal);
        this.stores.put(base.getName(), newSource);
    }

}
