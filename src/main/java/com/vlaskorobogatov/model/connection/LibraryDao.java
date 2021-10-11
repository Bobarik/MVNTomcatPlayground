package com.vlaskorobogatov.model.connection;

import org.postgresql.Driver;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class LibraryDao {
    Properties properties;

    public LibraryDao() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("sql.properties");
        properties = new Properties();
        properties.load(input);
    }

    public ResultSet getResult(PreparedStatement query) {
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
            if (connection != null) {
                return query.executeQuery();
            } else {
                System.out.println("Failed to connect.");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null;
    }

    public Integer doUpdate(PreparedStatement query) {
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/library", "postgres", "12345");
            if (connection != null) {
                return query.executeUpdate();
            } else {
                System.out.println("Failed to connect.");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null;
    }

    public PreparedStatement getStatement(String query) {
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/library", "postgres", "12345");
            if (connection != null) {
                return connection.prepareStatement(query);
            } else {
                System.out.println("Failed to connect.");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return null;
    }
}
