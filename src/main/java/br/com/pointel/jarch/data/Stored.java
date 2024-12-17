package br.com.pointel.jarch.data;

import org.apache.commons.dbcp2.BasicDataSource;

public class Stored {

    public final Helper helper;
    public final BasicDataSource source;

    public Stored(Helper helper, BasicDataSource source) {
        this.helper = helper;
        this.source = source;
    }

}
