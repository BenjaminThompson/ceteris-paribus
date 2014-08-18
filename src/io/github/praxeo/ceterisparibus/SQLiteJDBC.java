package io.github.praxeo.ceterisparibus;

/**
 * Created by bjt on 8/18/14.
 */

import java.sql.*;

public class SQLiteJDBC {
    private Connection CONNECTION = null;
    SQLiteJDBC(String database) {
        try {
            Class.forName("org.sqlite.JDBC");
            CONNECTION = DriverManager.getConnection("jdbc:sqlite:"+database);
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    public Connection getConnection() {
        return CONNECTION;
    }

}
