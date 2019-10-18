package vng.toanhda.service;

import disruptor.protobuf.PingRequest;
import disruptor.protobuf.PingResponse;
import disruptor.protobuf.PingServiceGrpc;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vng.toanhda.database.Database;
import vng.toanhda.database.DatabaseImpl;
import vng.toanhda.database.SQLClientProvider;
import vng.toanhda.database.SQLClientProviderVertX;
import vng.toanhda.metrics.Tracker;
import vng.toanhda.utils.JsonProtoUtils;

import java.util.List;

public class PingServiceHandler extends PingServiceGrpc.PingServiceVertxImplBase {
    private static final Logger logger =
            LoggerFactory.getLogger(PingServiceHandler.class.getCanonicalName());
    Database database;

    public PingServiceHandler(SQLClientProviderVertX sqlClientProviderVertX, SQLClientProvider sqlClientProvider) {
        this.database = new DatabaseImpl(sqlClientProviderVertX, sqlClientProvider);
    }

    @Override
    public void ping(PingRequest pingRequest, Future<PingResponse> response) {
        logger.info("pingRequest = {}", JsonProtoUtils.print(pingRequest), System.currentTimeMillis());
        Tracker.TrackerBuilder tracker = Tracker.builder().systemName("PingService").method("");

        Future<ResultSet> resultSetFuture = this.database.selectPing();
        resultSetFuture.setHandler(res -> {
            if (res.failed()) {
                response.fail(res.cause());
            } else {
                completeRequest(res.result().getRows(), pingRequest.getTimestamp(), response, tracker);
            }
        });
    }

    @Override
    public void pingWithDisruptor(PingRequest pingRequest, Future<PingResponse> response) {
        logger.info("pingRequest = {}", JsonProtoUtils.print(pingRequest), System.currentTimeMillis());
        Tracker.TrackerBuilder tracker = Tracker.builder().systemName("PingService").method("pingWithDisruptor");
        Future<ResultSet> resultSetFuture = this.database.selectPingWithDisruptor();
        resultSetFuture.setHandler(res -> {
            if (res.failed()) {
                response.fail(res.cause());
            } else {
                completeRequest(res.result().getRows(), pingRequest.getTimestamp(), response, tracker);
            }
        });
    }

    private void completeRequest(
            List<JsonObject> rows, long timestamp, Future<PingResponse> response, Tracker.TrackerBuilder tracker) {
        String uid = null;
        if (!rows.isEmpty()) {
            uid = rows.get(0).getString("uid");
        }
        PingResponse resPing = PingResponse.newBuilder()
                .setTimestamp(timestamp)
                .setSystemName(uid)
                .build();
        response.complete(resPing);
        tracker.build().record();
    }


}

