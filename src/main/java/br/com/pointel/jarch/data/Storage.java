package br.com.pointel.jarch.data;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;
import br.com.pointel.jarch.mage.WizChars;

public class Storage {

    private final Bases bases;
    private final Map<String, BasicDataSource> stores;

    public Storage() {
        this.bases = null;
        this.stores = null;
    }

    public Storage(Bases bases) {
        this.bases = bases;
        this.stores = new HashMap<>();
        this.start(bases);
    }

    private void start(Bases bases) {
        for (var base : bases) {
            var source = new BasicDataSource();
            source.setUrl(base.getUrl());
            var user = base.getUser();
            if (!WizChars.isEmpty(user)) {
                source.setUsername(user);
            }
            var pass = base.getPass();
            if (!WizChars.isEmpty(pass)) {
                source.setPassword(pass);
            }
            source.setMinIdle(base.storeMinIdle);
            source.setMaxIdle(base.storeMaxIdle);
            source.setMaxTotal(base.storeMaxTotal);
            this.stores.put(base.getName(), source);
        }
    }

    public Connection getLink(String ofBaseName) throws Exception {
        if (this.stores == null) {
            throw new Exception("No stores are served.");
        }
        var stored = this.stores.get(ofBaseName);
        if (stored == null) {
            throw new Exception("Base " + ofBaseName + " not found");
        }
        return stored.getConnection();
    }

    public EOrm getEOrm(String onBaseName) throws Exception {
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

    public ESql getESql(String onBaseName) throws Exception {
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

}
