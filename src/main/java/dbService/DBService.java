package dbService;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class DBService {
    private final Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(DBService.class);
    private static DBService instance;
    private static final Object obj = new Object();

    private DBService() {
        this.connection = getH2Connection();
    }

    public static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "admin";
            String pass = "admin";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            return DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            logger.error("Something terrible happened!", e);
            e.printStackTrace();
        }
        return null;
    }

    public static DBService getInstance() {
        if (instance == null) {
            synchronized (obj) {
                if (instance == null) {
                    instance = new DBService();
                }
            }
        }

        return instance;
    }
}