package vng.toanhda.database;

import io.vertx.core.Future;
import io.vertx.ext.sql.SQLClient;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLClientProvider {
    Future<Connection> getConnection();
}
