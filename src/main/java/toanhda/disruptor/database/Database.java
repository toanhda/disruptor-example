package toanhda.disruptor.database;

import io.vertx.core.Future;

import java.util.List;

public interface Database {
  Future<List<String>> selectPing();

  Future<List<String>> selectPingWithDisruptor();
}
