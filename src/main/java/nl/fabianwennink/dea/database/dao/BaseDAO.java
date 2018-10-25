package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.DatabaseProperties;
import nl.fabianwennink.dea.database.ResultInterface;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseDAO {

    private static final Logger LOGGER = Logger.getLogger(BaseDAO.class.getName());

    public BaseDAO() {
        loadDriver();
    }

    protected void performQuery(String query, ResultInterface processor, Object... bindings) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);

            // Bind the query bindings
            setQueryBindings(statement, bindings);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                processor.process(resultSet);
            }
        } catch(SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to execute query: " + e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }
    }

    protected boolean performUpdate(String query, Object... bindings) {
        boolean updated = false;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);

            // Bind the query bindings
            setQueryBindings(statement, bindings);

            updated = statement.executeUpdate() > 0;
        } catch(SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to execute query: " + e.getMessage());
        } finally {
            this.close(connection, statement, null);
        }

        return updated;
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

    private void setQueryBindings(PreparedStatement statement, Object... bindings) throws SQLException {
        int bindCount = 1;

        for(Object obj : bindings) {
            if(obj instanceof String) {
                statement.setString(bindCount, (String)obj);
            } else if(obj instanceof Integer) {
                statement.setInt(bindCount, (Integer)obj);
            } else if(obj instanceof Boolean) {
                statement.setBoolean(bindCount, (Boolean)obj);
            }

            bindCount++;
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
