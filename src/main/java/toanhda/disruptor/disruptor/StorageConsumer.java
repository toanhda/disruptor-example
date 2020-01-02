package toanhda.disruptor.disruptor;

import com.lmax.disruptor.WorkHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StorageConsumer implements WorkHandler<StorageEvent> {
  String SELECT_TEST = "SELECT * FROM table.account where  account_no ='9223379939013999965'";

  @Override
  public void onEvent(StorageEvent storageEvent) throws Exception {
    Thread.sleep(40);
    storageEvent.getFuture().complete();
  }
}
