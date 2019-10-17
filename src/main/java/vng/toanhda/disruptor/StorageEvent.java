package vng.toanhda.disruptor;

import com.lmax.disruptor.EventFactory;
import io.vertx.core.Future;
import io.vertx.ext.sql.SQLConnection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vng.toanhda.database.SQLClientProvider;
import vng.toanhda.metrics.Tracker;


@Setter
@Getter
public class StorageEvent {
    public static final EventFactory<StorageEvent> EVENT_FACTORY = new EventFactory<StorageEvent>() {
        @Override
        public StorageEvent newInstance() {
            return new StorageEvent();
        }
    };
    Future<Object> future;
    Tracker tracker;
    SQLConnection connection;
}

