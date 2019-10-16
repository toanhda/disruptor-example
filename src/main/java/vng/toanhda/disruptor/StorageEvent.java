package vng.toanhda.disruptor;

import com.lmax.disruptor.EventFactory;
import io.vertx.core.Future;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;

@Setter
@Getter
public class StorageEvent {
    public static final EventFactory<StorageEvent> EVENT_FACTORY = new EventFactory<StorageEvent>() {
        @Override
        public StorageEvent newInstance() {
            return new StorageEvent();
        }
    };
    Connection connection;
    Future<Object> future;
}

