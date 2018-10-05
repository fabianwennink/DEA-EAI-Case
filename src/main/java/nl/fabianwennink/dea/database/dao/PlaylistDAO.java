package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.util.ConnectionUtil;
import nl.fabianwennink.dea.database.util.DatabaseProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements GenericDAO<PlaylistDTO> {

    private static final String GET_ALL_PLAYLISTS_QUERY = "SELECT * from playlists";

    public PlaylistDAO() {
        loadDriver();
    }

    public List<PlaylistDTO> getAll() {
        List<PlaylistDTO> response = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DatabaseProperties.getConnectionString());
            statement = connection.prepareStatement(GET_ALL_PLAYLISTS_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PlaylistDTO dto = new PlaylistDTO();
                dto.setId(resultSet.getInt("id"));
                dto.setName(resultSet.getString("name"));
                dto.setOwner(resultSet.getBoolean("owner"));

                response.add(dto);
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
