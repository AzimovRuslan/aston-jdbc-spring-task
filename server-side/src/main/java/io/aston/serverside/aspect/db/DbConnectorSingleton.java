package io.aston.serverside.aspect.db;

import io.aston.serverside.aspect.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectorSingleton {
    private static final Connection CONNECTION = getInstance();

    private DbConnectorSingleton() {

    }

    private static Connection getInstance() {
        try {
            return DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static Connection getConnection() {
        return CONNECTION;
    }
}
