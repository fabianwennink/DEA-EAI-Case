package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.entities.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO extends BaseDAO {

    private static final String GET_ALL_PLAYLISTS_QUERY = "SELECT id, name, owner_id FROM playlist";
    private static final String GET_TOTAL_DURATION_QUERY = "SELECT SUM(duration) AS `duration` FROM track";
    private static final String UPDATE_PLAYLIST_NAME_QUERY = "UPDATE playlist SET name = ? WHERE id = ?";
    private static final String CREATE_NEW_PLAYLIST_QUERY = "INSERT INTO playlist (name, owner_id) VALUES(?, ?)";


    public List<Playlist> getAll() {
        List<Playlist> response = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(GET_ALL_PLAYLISTS_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(resultSet.getInt("id"));
                playlist.setName(resultSet.getString("name"));
                playlist.setOwnerId(resultSet.getInt("owner_id"));

                response.add(playlist);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return response;
    }

    public List<Playlist> editTitle(int playlistId, String name) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(UPDATE_PLAYLIST_NAME_QUERY);
            statement.setString(1, name);
            statement.setInt(2, playlistId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, null);
        }

        return getAll();
    }

    public boolean create(Playlist playlist) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(CREATE_NEW_PLAYLIST_QUERY);
            statement.setString(1, playlist.getName());
            statement.setInt(2, playlist.getOwnerId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            this.close(connection, statement, null);
        }

        return true;
    }

    public int getTotalDuration() {
        int duration = 0;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(GET_TOTAL_DURATION_QUERY);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                duration = resultSet.getInt("duration");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return duration;
    }
}
