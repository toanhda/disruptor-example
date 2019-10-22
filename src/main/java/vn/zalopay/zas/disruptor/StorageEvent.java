package vn.zalopay.zas.disruptor;

import com.lmax.disruptor.EventFactory;
import io.vertx.core.Future;
import lombok.Getter;
import lombok.Setter;
import vn.zalopay.zas.database.SQLClientProvider;
import vn.zalopay.zas.metrics.Tracker;

import java.util.List;

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
  Future<List<String>> future;
  Tracker tracker;
  SQLClientProvider provider;
}
