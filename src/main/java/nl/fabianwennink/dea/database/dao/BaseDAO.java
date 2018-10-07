package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.DatabaseProperties;

import java.sql.*;

public abstract class BaseDAO {

    public BaseDAO() {
        loadDriver();
    }

    protected Connection getConnection() {
        try {
            return DriverManager.getConnection(DatabaseProperties.getConnectionString());
        } catch (SQLException e) {
            System.out.println("Failed to open a connection. Error: " + e.getMessage());
        }

        return null;
    }

    protected void close(Connection connection, PreparedStatement statement, ResultSet result) {
        try {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (result != null) result.close();
        } catch (SQLException e) {
            System.out.println("Failed to close a connection. Error: " + e.getMessage());
        }
    }

    private void loadDriver() {
        try {
            Class.forName(DatabaseProperties.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
