package com.guide.multidb.config.rdb;

public interface RdbmsProperties {

    public String getResourceName();

    public String getDriverClassName();

    public String getJdbcUrl();

    public String getUserName();

    public String getPassword();

    public int getMinimumIdle();

    public int getMaximumPoolSize();

    public String getPackageToScan();
}
