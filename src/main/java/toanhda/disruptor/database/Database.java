package toanhda.disruptor.database;

import io.vertx.core.Future;

import java.util.List;

public interface Database {
  Future<Void> selectPing();

  Future<Void> selectPingWithDisruptor();
}
