package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.DatabaseProperties;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseDAO {

    private static final Logger LOGGER = Logger.getLogger(BaseDAO.class.getName());

    public BaseDAO() {
        loadDriver();
    }

    protected Connection getConnection() {
        try {
            return DriverManager.getConnection(DatabaseProperties.getConnectionString());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to open a connection. Error: " + e.getMessage());
        }

        return null;
    }

    protected void close(Connection connection, PreparedStatement statement, ResultSet result) {
        try {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (result != null) result.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to close a connection. Error: " + e.getMessage());
        }
    }

    private void loadDriver() {
        try {
            Class.forName(DatabaseProperties.getDriver());
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Failed to load SQL driver. Error: " + e.getMessage());
        }
    }
}
