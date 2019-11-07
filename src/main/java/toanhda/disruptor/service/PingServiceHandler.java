package toanhda.disruptor.service;

import disruptor.protobuf.PingRequest;
import disruptor.protobuf.PingResponse;
import disruptor.protobuf.PingServiceGrpc;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toanhda.disruptor.database.Database;
import toanhda.disruptor.database.DatabaseImpl;
import toanhda.disruptor.database.SQLClientProvider;
import toanhda.disruptor.metrics.Tracker;
import toanhda.disruptor.database.SQLClientProviderVertX;

import java.util.List;

public class PingServiceHandler extends PingServiceGrpc.PingServiceVertxImplBase {
  private static final Logger logger =
      LoggerFactory.getLogger(PingServiceHandler.class.getCanonicalName());
  Database database;

  public PingServiceHandler(
      SQLClientProviderVertX sqlClientProviderVertX, SQLClientProvider sqlClientProvider) {
    this.database = new DatabaseImpl(sqlClientProviderVertX, sqlClientProvider);
  }

  @Override
  public void ping(PingRequest pingRequest, Future<PingResponse> response) {
    Tracker tracker = Tracker.builder().systemName("PingService").method("ping").build();
    Future<List<String>> result = this.database.selectPing();
    result.setHandler(getAsyncResultHandler(pingRequest, response, tracker));
  }

  @Override
  public void pingWithDisruptor(PingRequest pingRequest, Future<PingResponse> response) {
    Tracker tracker =
        Tracker.builder().systemName("PingService").method("pingWithDisruptor").build();
    Future<List<String>> result = this.database.selectPingWithDisruptor();
    result.setHandler(getAsyncResultHandler(pingRequest, response, tracker));
  }

  private Handler<AsyncResult<List<String>>> getAsyncResultHandler(
      PingRequest pingRequest, Future<PingResponse> response, Tracker tracker) {
    return res -> {
      if (res.failed()) {
        response.fail(res.cause());
      } else {
        completeRequest(res.result(), pingRequest.getTimestamp(), response, tracker);
      }
    };
  }

  private void completeRequest(
      List<String> uids, long timestamp, Future<PingResponse> response, Tracker tracker) {
    String uid = null;
    if (!uids.isEmpty()) {
      uid = uids.get(0);
    }
    PingResponse resPing =
        PingResponse.newBuilder().setTimestamp(timestamp).setSystemName(uid).build();
    response.complete(resPing);
    tracker.record();
  }
}
