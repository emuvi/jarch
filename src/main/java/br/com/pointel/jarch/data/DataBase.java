package br.com.pointel.jarch.data;

import java.io.Serializable;

public enum DataBase implements Data, Serializable {

    SQLiteMemory(
                    "org.sqlite.JDBC",
                    "jdbc:sqlite::memory:",
                    null,
                    EOrmSQLite.class),

    SQLiteLocal(
                    "org.sqlite.JDBC",
                    "jdbc:sqlite:$path",
                    null,
                    EOrmSQLite.class),

    HSQLDBMemory(
                    "org.hsqldb.jdbcDriver",
                    "jdbc:hsqldb:mem:$data",
                    9000,
                    EOrmHSQL.class),

    HSQLDBLocal(
                    "org.hsqldb.jdbcDriver",
                    "jdbc:hsqldb:file:$path;hsqldb.lock_file=true",
                    9000,
                    EOrmHSQL.class),

    HSQLDBClient(
                    "org.hsqldb.jdbcDriver",
                    "jdbc:hsqldb:hsql://$path:$port/$data",
                    9000,
                    EOrmHSQL.class),

    DerbyInner(
                    "org.apache.derby.jdbc.EmbeddedDriver",
                    "jdbc:derby:$path;create=true",
                    1527,
                    EOrmDerby.class),

    DerbyClient(
                    "org.apache.derby.jdbc.ClientDriver",
                    "jdbc:derby://$path:$port/$data;create=true",
                    1527,
                    EOrmDerby.class),

    FirebirdLocal(
                    "org.firebirdsql.jdbc.FBDriver",
                    "jdbc:firebirdsql:local:$path",
                    3050,
                    EOrmFirebird.class),

    FirebirdInner(
                    "org.firebirdsql.jdbc.FBDriver",
                    "jdbc:firebirdsql:embedded:$path",
                    3050,
                    EOrmFirebird.class),

    FirebirdClient(
                    "org.firebirdsql.jdbc.FBDriver",
                    "jdbc:firebirdsql:$path:$port/$data",
                    3050,
                    EOrmFirebird.class),

    MySQLClient(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://$path:$port/$data",
                    3306,
                    EOrmMySQL.class),

    PostgreClient(
                    "org.postgresql.Driver",
                    "jdbc:postgresql://$path:$port/$data",
                    5432,
                    EOrmPostgre.class);

    public final String driverClazz;
    public final String formation;
    public final Integer defaultPort;
    public final Class<? extends EOrm> eOrmClazz;

    private DataBase(String driverClazz, String formation, Integer defaultPort,
                    Class<? extends EOrm> eOrmClazz) {
        this.driverClazz = driverClazz;
        this.formation = formation;
        this.defaultPort = defaultPort;
        this.eOrmClazz = eOrmClazz;
    }

    public String getUrlIdentity() {
        var dollarAt = this.formation.indexOf("$");
        if (dollarAt == -1) {
            return this.formation;
        }
        return this.formation.substring(0, dollarAt);
    }

    public static DataBase fromURL(String jdbc) {
        for (DataBase data : DataBase.values()) {
            if (jdbc.startsWith(data.getUrlIdentity())) {
                return data;
            }
        }
        return null;
    }

    public static Class<? extends EOrm> getEOrmClassFromURL(String jdbc) {
        for (DataBase data : DataBase.values()) {
            if (jdbc.startsWith(data.getUrlIdentity())) {
                return data.eOrmClazz;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.toChars();
    }

    public static DataBase fromChars(String chars) {
        return Data.fromChars(chars, DataBase.class);
    }

}
