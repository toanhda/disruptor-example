package vng.toanhda.database;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;

import javax.sql.DataSource;

public class SQLClientProviderProviderVertXIml extends ClientProvider implements SQLClientProviderVertX  {

    private final Vertx vertx;
    private SQLClient mySQLClient;
    DataSource dataSource;
    public SQLClientProviderProviderVertXIml(Vertx vertx) {
        this.vertx = vertx;
        this.dataSource = getDataSource();
    }


    @Override
    public void dispose() {
        if (mySQLClient != null) {
            mySQLClient.close();
        }
    }

    @Override
    public Future<Void> initialize() {

        Future<Void> future = Future.future();
        try {
            mySQLClient = JDBCClient.create(vertx, dataSource);
            if (mySQLClient == null) {
                throw new Exception("mySQLClient == null");
            }
        } catch (Exception e) {
            future.fail(e);
            return future;
        }


        return future;
    }

    @Override
    public SQLClient getClientVertX() {
        return mySQLClient;
    }

}
