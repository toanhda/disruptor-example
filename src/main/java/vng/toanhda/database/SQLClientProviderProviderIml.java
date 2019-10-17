package vng.toanhda.database;

import io.vertx.core.Future;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLClientProviderProviderIml extends ClientProvider implements SQLClientProvider {
    DataSource dataSource;

    public SQLClientProviderProviderIml() {
        this.dataSource = getDataSource();
    }


    @Override
    public Future<Connection> getConnection() {
        Future<Connection> future = Future.future();
        try {
            future.complete(dataSource.getConnection());
        } catch (SQLException e) {
            future.fail(e);
        }
        return future;
    }
}
