package toanhda.disruptor.database;

import com.lmax.disruptor.RingBuffer;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import toanhda.disruptor.disruptor.DisruptorCreator;
import toanhda.disruptor.disruptor.StorageEvent;
import toanhda.disruptor.metrics.Tracker;

public class DatabaseImpl implements Database {
  private SQLClientProviderVertX clientProviderVertX;
  private SQLClientProvider clientProvider;
  private String SELECT_TEST =
      "SELECT * FROM table.account where  account_no ='9223379939013999965'";
  private Vertx vertx;

  public DatabaseImpl(Vertx vertx) {
    this.vertx = vertx;
  }

  public Future<Void> selectPing() {
    Tracker tracker =
        Tracker.builder().systemName("PingServiceCallDatabase").method("ping").build();
    Future<Void> fu = Future.future();
    vertx.executeBlocking(
        future -> {
          try {
            Thread.sleep(40);
            tracker.record();
            fu.complete();
          } catch (Exception e) {
            fu.fail(e);
          }
        },
        res -> {});
    return fu;
  }

  @Override
  public Future<Void> selectPingWithDisruptor() {
    Tracker tracker =
        Tracker.builder().systemName("PingServiceCallDatabase").method("pingWithDisruptor").build();
    Future<Void> future = Future.future();

    RingBuffer<StorageEvent> ringBuffer = DisruptorCreator.getRingBuffer();
    long sequenceId = ringBuffer.next();
    StorageEvent storageEvent = ringBuffer.get(sequenceId);
    storageEvent.setFuture(future);
    storageEvent.setTracker(tracker);
    ringBuffer.publish(sequenceId);

    return future;
  }
}
