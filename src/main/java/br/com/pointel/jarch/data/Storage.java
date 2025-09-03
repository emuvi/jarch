package br.com.pointel.jarch.data;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;
import br.com.pointel.jarch.mage.WizString;

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
        for (var dataWays : bases) {
            newSourceOnStore(dataWays);
        }
    }

    private void newSourceOnStore(BasedWays dataWays) {
        if (dataWays == null) {
            throw new RuntimeException("DataWays cannot be null.");
        }
        var name = dataWays.getName();
        if (WizString.isEmpty(name)) {
            throw new RuntimeException("DataWays name cannot be empty.");
        }
        var url = dataWays.getUrl();
        if (WizString.isEmpty(url)) {
            throw new RuntimeException("DataWays URL cannot be empty.");
        }
        if (dataWays.dataJdbc == null && dataWays.dataLink == null) {
            throw new RuntimeException("Either dataJdbc or dataLink must be provided in DataWays.");
        }
        if (dataWays.dataJdbc != null && dataWays.dataLink != null) {
            throw new RuntimeException("Only one of dataJdbc or dataLink should be provided in DataWays.");
        }
        var newSource = new BasicDataSource();
        newSource.setUrl(url);
        var user = dataWays.getUser();
        if (!WizString.isEmpty(user)) {
            newSource.setUsername(user);
        }
        var pass = dataWays.getPass();
        if (!WizString.isEmpty(pass)) {
            newSource.setPassword(pass);
        }
        newSource.setMinIdle(dataWays.poolMinIdle);
        newSource.setMaxIdle(dataWays.poolMaxIdle);
        newSource.setMaxTotal(dataWays.poolMaxTotal);
        this.stores.put(name, newSource);
    }

}
