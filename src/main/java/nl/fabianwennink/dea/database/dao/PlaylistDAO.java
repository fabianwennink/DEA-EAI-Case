package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO extends BaseDAO {

    private static final String GET_ALL_PLAYLISTS_QUERY = "SELECT * FROM playlists";

    public List<PlaylistDTO> getAll() {
        List<PlaylistDTO> response = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.getConnection();
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
            this.close(connection, statement, resultSet);
        }

        return response;
    }
}
