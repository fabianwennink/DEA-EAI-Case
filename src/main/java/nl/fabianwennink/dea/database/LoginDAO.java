package nl.fabianwennink.dea.database;

import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.database.util.DatabaseProperties;

import java.sql.*;

public class LoginDAO implements GenericDAO<LoginResponseDTO> {

    private DatabaseProperties databaseProperties;

    public LoginDAO(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;

        loadDriver();
    }

    public LoginResponseDTO select(String username, String password) {
        LoginResponseDTO response = new LoginResponseDTO();

        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());

            PreparedStatement statement = connection.prepareStatement("SELECT name, token from Users WHERE name = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                response.setUser(resultSet.getString("name"));
                response.setToken(resultSet.getString("token"));
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public void loadDriver() {
        try {
            Class.forName(databaseProperties.driver());
        } catch (ClassNotFoundException e) {}
    }
}
