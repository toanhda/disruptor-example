package vng.toanhda.service;

import disruptor.protobuf.PingRequest;
import disruptor.protobuf.PingResponse;
import disruptor.protobuf.PingServiceGrpc;
import io.vertx.core.Future;
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
        Tracker tracker = Tracker.builder().systemName("PingService").method("ping").build();

        Future<List<String>> result = this.database.selectPing();
        result.setHandler(res -> {
            if (res.failed()) {
                response.fail(res.cause());
            } else {
                completeRequest(res.result(), pingRequest.getTimestamp(), response, tracker);
            }
        });
    }

    @Override
    public void pingWithDisruptor(PingRequest pingRequest, Future<PingResponse> response) {
        logger.info("pingRequest = {}", JsonProtoUtils.print(pingRequest), System.currentTimeMillis());
        Tracker tracker = Tracker.builder().systemName("PingService").method("pingWithDisruptor").build();
        Future<List<String>> result = this.database.selectPingWithDisruptor();
        result.setHandler(res -> {
            if (res.failed()) {
                response.fail(res.cause());
            } else {
                completeRequest(res.result(), pingRequest.getTimestamp(), response, tracker);
            }
        });
    }

    private void completeRequest(
            List<String> uids, long timestamp, Future<PingResponse> response, Tracker tracker) {
        String uid = null;
        if (!uids.isEmpty()) {
            uid = uids.get(0);
        }
        PingResponse resPing = PingResponse.newBuilder()
                .setTimestamp(timestamp)
                .setSystemName(uid)
                .build();
        response.complete(resPing);
        tracker.record();
    }


}

