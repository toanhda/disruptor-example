package toanhda.disruptor.service;

import disruptor.protobuf.PingRequest;
import disruptor.protobuf.PingResponse;
import disruptor.protobuf.PingServiceGrpc;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toanhda.disruptor.database.Database;
import toanhda.disruptor.database.DatabaseImpl;
import toanhda.disruptor.metrics.Tracker;

public class PingServiceHandler extends PingServiceGrpc.PingServiceVertxImplBase {
  private static final Logger logger =
      LoggerFactory.getLogger(PingServiceHandler.class.getCanonicalName());
  Database database;

  public PingServiceHandler(Vertx vertx) {
    this.database = new DatabaseImpl(vertx);
  }

  @Override
  public void ping(PingRequest pingRequest, Future<PingResponse> response) {
    Tracker tracker = Tracker.builder().systemName("PingService").method("ping").build();
    Future<Void> result = this.database.selectPing();
    result.setHandler(getAsyncResultHandler(pingRequest, response, tracker));
  }

  @Override
  public void pingWithDisruptor(PingRequest pingRequest, Future<PingResponse> response) {
    Tracker tracker =
        Tracker.builder().systemName("PingService").method("pingWithDisruptor").build();
    Future<Void> result = this.database.selectPingWithDisruptor();
    result.setHandler(getAsyncResultHandler(pingRequest, response, tracker));
  }

  private Handler<AsyncResult<Void>> getAsyncResultHandler(
      PingRequest pingRequest, Future<PingResponse> response, Tracker tracker) {
    return res -> {
      if (res.failed()) {
        response.fail(res.cause());
      } else {
        completeRequest(pingRequest.getTimestamp(), response, tracker);
      }
    };
  }

  private void completeRequest(long timestamp, Future<PingResponse> response, Tracker tracker) {
    String uid = "1";

    PingResponse resPing =
        PingResponse.newBuilder().setTimestamp(timestamp).setSystemName(uid).build();
    response.complete(resPing);
    tracker.record();
  }
}
