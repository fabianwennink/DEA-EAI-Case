package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.DatabaseProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseDAO {

    private static final Logger LOGGER = Logger.getLogger(BaseDAO.class.getName());

    public BaseDAO() {
        loadDriver();
    }

    protected List<Map<String, Object>> performQuery(String query, Object... bindings) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        List<Map<String, Object>> rows = null;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(query);

            // Bind the query bindings
            this.setQueryBindings(statement, bindings);

            resultSet = statement.executeQuery();

            if(resultSet != null) {
                rows = resultSetToList(resultSet);
            }
        } catch(SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to execute query: " + e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return rows;
    }

    protected boolean performUpdate(String query, Object... bindings) {
        boolean updated = false;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(query);

            // Bind the query bindings
            this.setQueryBindings(statement, bindings);

            if(statement.executeUpdate() > 0) {
                updated = true;
            }
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

    private List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();

        List<Map<String, Object>> rows = new ArrayList<>();

        while (rs.next()){
            Map<String, Object> row = new HashMap<>(columns);

            for(int i = 1; i <= columns; ++i){
                row.put(md.getColumnName(i), rs.getObject(i));
            }

            rows.add(row);
        }

        return rows;
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
