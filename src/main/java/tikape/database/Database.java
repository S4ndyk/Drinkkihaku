package tikape.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static String databaseAddress = "jdbc:sqlite:db/database.db";

    public Database() throws ClassNotFoundException {
        databaseAddress = databaseAddress;
    }

    public static Connection getConnection() throws Exception {
    String dbUrl = System.getenv("JDBC_DATABASE_URL");
    if (dbUrl != null && dbUrl.length() > 0) {
        return DriverManager.getConnection(dbUrl);
    }

    return DriverManager.getConnection("jdbc:sqlite:db/database.db");
}
}
