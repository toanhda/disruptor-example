package vng.toanhda.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLClientProviderProviderIml extends ClientProvider implements SQLClientProvider {
    DataSource dataSource;
    public SQLClientProviderProviderIml() {
        this.dataSource = getDataSource();
    }


    @Override
    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
