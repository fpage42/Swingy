package fr.fpage.swingy;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final String dbPath;
    private Connection connection;

    public Database(String dbPath) {
        this.dbPath = dbPath;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed())
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.dbPath);
        return this.connection;
    }
}
