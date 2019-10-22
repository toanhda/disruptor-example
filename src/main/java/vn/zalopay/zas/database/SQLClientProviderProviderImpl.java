package vn.zalopay.zas.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLClientProviderProviderImpl extends ClientProvider implements SQLClientProvider {
  DataSource dataSource;

  public SQLClientProviderProviderImpl() {
    this.dataSource = getDataSource();
  }

  @Override
  public Connection getConnection() {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
