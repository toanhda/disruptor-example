package vn.zalopay.zas.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class ClientProvider {
  public DataSource getDataSource() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName("com.mysql.cj.jdbc.Driver");
    config.setJdbcUrl("jdbc:mysql://10.30.17.173:4000/zas_qc?autoReconnect=true");
    config.setUsername("dbgtest");
    config.setPassword("abc@123");
    config.setMaximumPoolSize(64);
    config.setAutoCommit(true);
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    config.addDataSourceProperty("maxLifetime", "600000");

    return new HikariDataSource(config);
  }
}
