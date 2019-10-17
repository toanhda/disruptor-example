package vng.toanhda.service;

import disruptor.protobuf.PingRequest;
import disruptor.protobuf.PingResponse;
import disruptor.protobuf.PingServiceGrpc;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vng.toanhda.config.ServerConfig;
import vng.toanhda.database.Database;
import vng.toanhda.database.DatabaseIml;
import vng.toanhda.database.SQLClientProvider;
import vng.toanhda.database.SQLClientProviderVertX;
import vng.toanhda.metrics.Tracker;
import vng.toanhda.utils.JsonProtoUtils;

import java.util.List;

public class PingServiceHandler extends PingServiceGrpc.PingServiceVertxImplBase {
    Database database;
    private static final Logger logger =
            LoggerFactory.getLogger(PingServiceHandler.class.getCanonicalName());
    public PingServiceHandler(SQLClientProviderVertX sqlClientProviderVertX, SQLClientProvider sqlClientProvider) {
        this.database = new DatabaseIml(sqlClientProviderVertX, sqlClientProvider);
    }

    @Override
    public void ping(PingRequest pingRequest, Future<PingResponse> response) {
        logger.info("pingRequest = {}", JsonProtoUtils.print(pingRequest), System. currentTimeMillis());
        Tracker.TrackerBuilder tracker = Tracker.builder().systemName("PingService").method("");

        Future<ResultSet> resultSetFuture = this.database.selectPing();
        resultSetFuture.setHandler(res -> {
            if (res.failed()) {
                response.fail(res.cause());
            } else {
                List<JsonObject> rows = res.result().getRows();
                String uid = null;
                if (!rows.isEmpty()) {
                    uid = rows.get(0).getString("uid");
                }
                PingResponse resPing = PingResponse.newBuilder()
                        .setTimestamp(pingRequest.getTimestamp())
                        .setSystemName(uid)
                        .build();
                response.complete(resPing);
                tracker.build().record();
            }
        });
    }

    @Override
    public void pingWithDisruptor(PingRequest pingRequest, Future<PingResponse> response) {
        logger.info("pingRequest = {}", JsonProtoUtils.print(pingRequest), System. currentTimeMillis());
        Tracker.TrackerBuilder tracker = Tracker.builder().systemName("PingService").method("pingWithDisruptor");
        Future<List<String>> resultSetFuture = this.database.selectPingWithDisruptor();
        resultSetFuture.setHandler(res -> {
            if (res.failed()) {
                response.fail(res.cause());
            } else {
                List<String> rows = res.result();
                String uid = null;
                if (!rows.isEmpty()) {
                    uid = rows.get(0);
                }
                PingResponse resPing = PingResponse.newBuilder()
                        .setTimestamp(pingRequest.getTimestamp())
                        .setSystemName(uid)
                        .build();
                response.complete(resPing);
                tracker.build().record();
            }
        });
    }

}

