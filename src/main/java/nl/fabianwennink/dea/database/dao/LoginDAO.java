package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.database.util.ConnectionUtil;
import nl.fabianwennink.dea.database.util.DatabaseProperties;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LoginDAO implements GenericDAO<LoginResponseDTO> {

    private static final String LOGIN_QUERY = "SELECT name, token from users WHERE name = ? AND password = ?";

    public LoginDAO() {
        loadDriver();
    }

    public LoginResponseDTO getUser(String username, String password) {
        LoginResponseDTO response = new LoginResponseDTO();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DatabaseProperties.getConnectionString());

            statement = connection.prepareStatement(LOGIN_QUERY);
            statement.setString(1, username);
            statement.setString(2, password);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                response.setUser(resultSet.getString("name"));
                response.setToken(resultSet.getString("token"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionUtil.close(connection, statement, resultSet);
        }

        return response;
    }

    @Override
    public void loadDriver() {
        try {
            Class.forName(DatabaseProperties.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
