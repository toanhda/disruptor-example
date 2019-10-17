package vng.toanhda.disruptor;

import com.lmax.disruptor.EventHandler;
import io.vertx.ext.sql.SQLConnection;

import java.util.ArrayList;
import java.util.List;

public class StorageConsumer implements EventHandler<StorageEvent> {
    String SELECT_TEST = "SELECT * FROM zas_dev.account where  account_no ='9223738833213728270'";

    @Override
    public void onEvent(StorageEvent storageEvent, long sequence, boolean endOfBatch) throws Exception {
        SQLConnection connection = storageEvent.getConnection();
        if (connection != null) {
            connection.query(SELECT_TEST, select -> {
                storageEvent.getTracker().record();
                if (select.failed()) {
                    return;
                }
                List<String> uids = new ArrayList<>();
                uids.add(select.result().getRows().get(0).getString("uid"));
                storageEvent.getFuture().complete(uids);
                connection.close();
            });
        }
    }
}
