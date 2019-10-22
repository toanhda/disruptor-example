package vn.zalopay.zas.database;

import io.vertx.core.Future;
import io.vertx.ext.sql.SQLClient;

public interface SQLClientProviderVertX {
  Future<Void> initialize();

  SQLClient getClientVertX();

  void dispose();
}
