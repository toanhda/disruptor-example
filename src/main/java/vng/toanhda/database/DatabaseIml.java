package vng.toanhda.database;

import com.lmax.disruptor.RingBuffer;
import io.vertx.core.Future;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import vng.toanhda.disruptor.StorageEvent;
import vng.toanhda.disruptor.DisruptorCreator;
import vng.toanhda.metrics.Tracker;

import java.sql.SQLException;
import java.util.List;


public class DatabaseIml implements Database {
    private SQLClientProviderVertX clientProviderVertX;
    private SQLClientProvider clientProvider;
    private String SELECT_TEST = "SELECT * FROM zas_dev.account where  account_no ='9223738833213728270'";

    public DatabaseIml(SQLClientProviderVertX clientProviderVertX, SQLClientProvider clientProvider) {
        this.clientProviderVertX = clientProviderVertX;
        this.clientProvider = clientProvider;
    }


    public Future<ResultSet> selectPing() {
        Tracker.TrackerBuilder tracker = Tracker.builder().systemName("PingServiceCallDatabase").method("ping");
        Future<ResultSet> response = Future.future();
        clientProviderVertX.getClientVertX().getConnection(ar -> {
            SQLConnection connection = ar.result();
            connection.query(SELECT_TEST, select -> {
                if (select.failed()) {
                    response.fail(select.cause());
                    return;
                }
                response.complete(select.result());
                connection.close();
            });
        });
        tracker.build().record();
        return response;
    }

    @Override
    public Future<List<String>> selectPingWithDisruptor() {
        Tracker.TrackerBuilder tracker = Tracker.builder().systemName("PingServiceCallDatabase").method("pingWithDisruptor");
        Future future = Future.future();
        consumerRequest(future);
        tracker.build().record();
        return future;

    }

    private void consumerRequest(Future future){
        RingBuffer<StorageEvent> ringBuffer = DisruptorCreator.getRingBuffer();
        long sequenceId = ringBuffer.next();
        StorageEvent storageEvent = ringBuffer.get(sequenceId);
        storageEvent.setConnection(clientProvider.getConnection());
        storageEvent.setFuture(future);
        ringBuffer.publish(sequenceId);
    }
}
