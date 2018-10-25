package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.Playlist;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlaylistDAO extends BaseDAO {

    private static final String GET_ALL_PLAYLISTS_QUERY = "SELECT id, name, owner_id FROM playlist";
    private static final String GET_TOTAL_DURATION_QUERY = "SELECT SUM(duration) AS `duration` FROM track";
    private static final String UPDATE_PLAYLIST_NAME_QUERY = "UPDATE playlist SET name = ? WHERE id = ? AND owner_id = ?";
    private static final String CREATE_NEW_PLAYLIST_QUERY = "INSERT INTO playlist (name, owner_id) VALUES(?, ?)";
    private static final String DELETE_PLAYLIST_QUERY = "DELETE FROM playlist WHERE id = ? AND owner_id = ?";
    private static final String IS_OWNED_BY_USER_QUERY = "SELECT 1 FROM playlist WHERE id = ? AND owner_id = ?";

    public List<Playlist> getAll() {
        List<Playlist> response = new ArrayList<>();

        List<Map<String, Object>> results = performQuery(GET_ALL_PLAYLISTS_QUERY);

        for(Map<String, Object> row : results) {
            Playlist playlist = new Playlist();
            playlist.setId((Integer)row.get("id"));
            playlist.setName((String)row.get("name"));
            playlist.setOwnerId((Integer)row.get("owner_id"));

            response.add(playlist);
        }

        return response;
    }

    public boolean editTitle(int playlistId, String name, int userId) {
        return this.performUpdate(UPDATE_PLAYLIST_NAME_QUERY, name, playlistId, userId);
    }

    public boolean create(Playlist playlist) {
        return this.performUpdate(CREATE_NEW_PLAYLIST_QUERY, playlist.getName(), playlist.getOwnerId());
    }

    public boolean delete(int playlistId, int userId) {
        return this.performUpdate(DELETE_PLAYLIST_QUERY, playlistId, userId);
    }

    public int getTotalDuration() {
        int duration = 0;

        List<Map<String, Object>> results = performQuery(GET_TOTAL_DURATION_QUERY);

        for(Map<String, Object> row : results) {
            duration = ((BigDecimal)row.get("duration")).intValue();
        }

        return duration;
    }

    public boolean isOwnedByUser(int playlistId, int userId) {
        return this.performQuery(IS_OWNED_BY_USER_QUERY, playlistId, userId).size() > 0;
    }
}
