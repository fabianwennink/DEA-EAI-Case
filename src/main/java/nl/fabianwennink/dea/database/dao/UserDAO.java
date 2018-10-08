package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.User;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO extends BaseDAO {

    private static final String LOGIN_QUERY = "SELECT id, name, token from users WHERE name = ? AND password = ?";

    public User getUser(String username, String password) {
        User user = new User();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        try {
            connection = this.getConnection();

            statement = connection.prepareStatement(LOGIN_QUERY);
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
}
