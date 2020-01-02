package toanhda.disruptor.disruptor;

import com.lmax.disruptor.EventFactory;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.Getter;
import lombok.Setter;
import toanhda.disruptor.database.SQLClientProvider;
import toanhda.disruptor.metrics.Tracker;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Setter
@Getter
public class StorageEvent {
  public static final EventFactory<StorageEvent> EVENT_FACTORY =
      new EventFactory<StorageEvent>() {
        @Override
        public StorageEvent newInstance() {
          return new StorageEvent();
        }
      };
  Future<Void> future = Future.future();
  Tracker tracker;
  Vertx vertx;
//  SQLClientProvider provider;
}
