package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.DatabaseProperties;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseDAO {

    private static final Logger LOGGER = Logger.getLogger(BaseDAO.class.getName());

    public BaseDAO() {
        loadDriver();
    }

    protected List<Object> performQuery(String query, Object... bindings) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(query);

            // Bind the query bindings
            this.setQueryBindings(statement, bindings);



        } catch(SQLException e) {

        } finally {
            this.close(connection, statement, resultSet);
        }

        return null;
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

    private PreparedStatement setQueryBindings(PreparedStatement statement, Object... bindings) throws SQLException {
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

        return statement;
    }

    private void loadDriver() {
        try {
            Class.forName(DatabaseProperties.getDriver());
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Failed to load SQL driver. Error: " + e.getMessage());
        }
    }
}
