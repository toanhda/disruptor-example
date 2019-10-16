package vng.toanhda.database;

import io.vertx.core.Future;
import io.vertx.ext.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

public interface Database {
    Future<ResultSet> selectPing();
    Future<List<String>> selectPingWithDisruptor();
}
