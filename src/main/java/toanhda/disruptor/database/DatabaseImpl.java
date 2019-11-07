package toanhda.disruptor.database;

import com.lmax.disruptor.RingBuffer;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.SQLConnection;
import toanhda.disruptor.disruptor.DisruptorCreator;
import toanhda.disruptor.disruptor.StorageEvent;
import toanhda.disruptor.metrics.Tracker;

import java.util.ArrayList;
import java.util.List;

public class DatabaseImpl implements Database {
  private SQLClientProviderVertX clientProviderVertX;
  private SQLClientProvider clientProvider;
  private String SELECT_TEST =
      "SELECT * FROM table.account where  account_no ='9223379939013999965'";

  public DatabaseImpl(
      SQLClientProviderVertX clientProviderVertX, SQLClientProvider clientProvider) {
    this.clientProviderVertX = clientProviderVertX;
    this.clientProvider = clientProvider;
  }

  public Future<List<String>> selectPing() {
    Tracker.TrackerBuilder tracker =
        Tracker.builder().systemName("PingServiceCallDatabase").method("ping");
    Future<List<String>> response = Future.future();
    clientProviderVertX
        .getClientVertX()
        .getConnection(
            ar -> {
              SQLConnection connection = ar.result();
              connection.query(
                  SELECT_TEST,
                  select -> {
                    tracker.build().record();
                    if (select.failed()) {
                      response.fail(select.cause());
                      return;
                    }
                    List<String> uids = new ArrayList<>();

                    List<JsonObject> rows = select.result().getRows();
                    if (!rows.isEmpty()) {
                      uids.add(rows.get(0).getString("uid"));
                    }
                    response.complete(uids);
                    connection.close();
                  });
            });
    return response;
  }

  @Override
  public Future<List<String>> selectPingWithDisruptor() {
    Tracker tracker =
        Tracker.builder().systemName("PingServiceCallDatabase").method("pingWithDisruptor").build();
    Future<List<String>> future = Future.future();

    RingBuffer<StorageEvent> ringBuffer = DisruptorCreator.getRingBuffer();
    long sequenceId = ringBuffer.next();
    StorageEvent storageEvent = ringBuffer.get(sequenceId);
    storageEvent.setProvider(clientProvider);
    storageEvent.setFuture(future);
    storageEvent.setTracker(tracker);
    ringBuffer.publish(sequenceId);

    return future;
  }
}
