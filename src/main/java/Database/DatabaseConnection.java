package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection databaseConnection;
    private Connection connection;

    private DatabaseConnection() {
        // Initialize the database connection here
        String jdbcUrl = "jdbc:mysql://localhost:3306/restaurant";
        String username = "root";
        String password = "0000";

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        if (databaseConnection == null) {
            ///It ensures that only one thread at a time can execute the code within the block. The synchronization
            // is done on the class object (DatabaseConnection.class), ensuring that multiple threads cannot
            // simultaneously create new instances.
            synchronized (DatabaseConnection.class) {
                if (databaseConnection == null) {
                    System.out.println("DATABASE CONNECTION MADE");
                    databaseConnection = new DatabaseConnection();
                }
            }
        }
        return databaseConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
