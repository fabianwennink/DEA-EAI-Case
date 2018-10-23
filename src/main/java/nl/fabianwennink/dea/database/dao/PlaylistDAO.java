package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO extends BaseDAO {

    private static final String GET_ALL_PLAYLISTS_QUERY = "SELECT id, name, owner_id FROM playlist";
    private static final String GET_TOTAL_DURATION_QUERY = "SELECT SUM(duration) AS `duration` FROM track";
    private static final String UPDATE_PLAYLIST_NAME_QUERY = "UPDATE playlist SET name = ? WHERE id = ? AND owner_id = ?";
    private static final String CREATE_NEW_PLAYLIST_QUERY = "INSERT INTO playlist (name, owner_id) VALUES(?, ?)";
    private static final String DELETE_PLAYLIST_QUERY = "DELETE FROM playlist WHERE id = ? AND owner_id = ?";
    private static final String IS_OWNED_BY_USER_QUERY = "SELECT 1 FROM playlist WHERE id = ? AND owner_id = ?";

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

    public boolean editTitle(int playlistId, String name, int userId) {
        boolean stored = false;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(UPDATE_PLAYLIST_NAME_QUERY);
            statement.setString(1, name);
            statement.setInt(2, playlistId);
            statement.setInt(3, userId);

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

    public boolean create(Playlist playlist) {
        boolean stored = false;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(CREATE_NEW_PLAYLIST_QUERY);
            statement.setString(1, playlist.getName());
            statement.setInt(2, playlist.getOwnerId());

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

    public boolean delete(int playlistId, int userId) {
        boolean deleted = false;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(DELETE_PLAYLIST_QUERY);
            statement.setInt(1, playlistId);
            statement.setInt(2, userId);

            if(statement.executeUpdate() > 0) {
                deleted = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, null);
        }

        return deleted;
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

    public boolean isOwnedByUser(int playlistId, int userId) {
        boolean ownedBy = false;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = this.getConnection();
            statement = connection.prepareStatement(IS_OWNED_BY_USER_QUERY);
            statement.setInt(1, playlistId);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();

            if(resultSet.next()) {
                ownedBy = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return ownedBy;
    }
}
