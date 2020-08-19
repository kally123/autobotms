package com.autobot.domain;

public class DatabaseSetup {

    private String databaseProvider;

    private String database;

    private String userName;

    private String password;

    private String hostUrl;
    private String dbName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDatabaseProvider() {
        return databaseProvider;
    }

    public void setDatabaseProvider(String databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "DatabaseSetup [databaseProvider=" + databaseProvider + ", database=" + database + ", userName="
                + userName + ", password=" + password + ", hostUrl=" + hostUrl + ", dbName=" + dbName + "]";
    }

}
