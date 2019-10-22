package vn.zalopay.zas.database;

import java.sql.Connection;

public interface SQLClientProvider {
  Connection getConnection();
}
