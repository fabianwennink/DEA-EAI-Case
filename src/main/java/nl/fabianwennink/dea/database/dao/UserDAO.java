package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.User;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO extends BaseDAO {

    private static final String LOGIN_PASSWORD_USERNAME_QUERY = "SELECT id, name from user WHERE username = ? AND password = ?";
    private static final String FETCH_USER_BY_TOKEN_QUERY = "SELECT id, name from user WHERE token = ?";
    private static final String STORE_USER_TOKEN_QUERY = "UPDATE user SET token = ? WHERE id = ?";

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
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return user;
    }

    public User verifyToken(String token) {
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

    public boolean storeToken(User user) {
        boolean stored = false;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.getConnection();

            statement = connection.prepareStatement(STORE_USER_TOKEN_QUERY);
            statement.setString(1, user.getToken());
            statement.setInt(2, user.getId());

            if(statement.executeUpdate() > 0) {
                stored = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, null);
        }

        return stored;
    }
}
