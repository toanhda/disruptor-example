package toanhda.disruptor.database;

import java.sql.Connection;

public interface SQLClientProvider {
  Connection getConnection();
}
