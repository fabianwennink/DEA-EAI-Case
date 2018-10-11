package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.User;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO extends BaseDAO {

    private static final String LOGIN_PASSWORD_USERNAME_QUERY = "SELECT id, name, token from user WHERE name = ? AND password = ?";
    private static final String FETCH_USER_BY_TOKEN_QUERY = "SELECT id, name from user WHERE token = ?";

    public User getUser(String username, String password) {
        User user = new User();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        try {
            connection = this.getConnection();

            statement = connection.prepareStatement(LOGIN_PASSWORD_USERNAME_QUERY);
            statement.setString(1, username);
            statement.setString(2, password);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setToken(resultSet.getString("token"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return user;
    }

    public User getUser(String token) {
        User user = new User();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        try {
            connection = this.getConnection();

            statement = connection.prepareStatement(FETCH_USER_BY_TOKEN_QUERY);
            statement.setString(1, token);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setToken(token);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return user;
    }
}
