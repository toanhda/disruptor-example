package vng.toanhda.disruptor;

import com.lmax.disruptor.EventHandler;
import io.vertx.ext.sql.SQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StorageConsumer implements EventHandler<StorageEvent> {
    String SELECT_TEST = "SELECT * FROM zas_dev.account where  account_no ='9223738833213728270'";

    @Override
    public void onEvent(StorageEvent storageEvent, long sequence, boolean endOfBatch) throws Exception {
        Connection connection = storageEvent.getProvider().getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(SELECT_TEST);
        storageEvent.getTracker().record();
        List<String> uids = new ArrayList<>();
        while(rs.next()){
            uids.add(rs.getString("uid"));
        }
        storageEvent.getFuture().complete(uids);
        rs.close();
        stmt.close();
        connection.close();
    }
}
