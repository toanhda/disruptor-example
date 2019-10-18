package vng.toanhda.database;

import io.vertx.core.Future;
import io.vertx.ext.sql.ResultSet;

public interface Database {
    Future<ResultSet> selectPing();
    Future<ResultSet> selectPingWithDisruptor();
}
