package vng.toanhda.disruptor;

import com.lmax.disruptor.EventHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class StorageConsumer implements EventHandler<StorageEvent> {
    String SELECT_TEST = "SELECT * FROM zas_dev.account where  account_no ='9223738833213728270'";

    @Override
    public void onEvent(StorageEvent storageEvent, long sequence, boolean endOfBatch) throws Exception {
        Connection conn = storageEvent.getConnection();
        PreparedStatement pst = conn.prepareStatement(SELECT_TEST);
        java.sql.ResultSet rs = pst.executeQuery();

        List<String> uids = new ArrayList<>();
        while (rs.next()) {
            uids.add(rs.getString("uid"));
        }
        storageEvent.getFuture().complete(uids);
        conn.close();
    }
}
