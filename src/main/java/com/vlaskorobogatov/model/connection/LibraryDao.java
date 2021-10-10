package com.vlaskorobogatov.model.connection;

import org.postgresql.Driver;

import java.sql.*;

public class LibraryDao {
    public ResultSet getResult(PreparedStatement query) {
        try {
            DriverManager.registerDriver(new Driver());
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/library", "postgres", "12345");
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
