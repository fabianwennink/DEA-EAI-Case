package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PlaylistDAO extends BaseDAO {

    private static final String GET_ALL_PLAYLISTS_QUERY = "SELECT id, name, owner_id FROM playlist";
    private static final String GET_TOTAL_DURATION_QUERY = "SELECT SUM(duration) AS `duration` FROM track";
    private static final String UPDATE_PLAYLIST_NAME_QUERY = "UPDATE playlist SET name = ? WHERE id = ? AND owner_id = ?";
    private static final String CREATE_NEW_PLAYLIST_QUERY = "INSERT INTO playlist (name, owner_id) VALUES(?, ?)";
    private static final String DELETE_PLAYLIST_QUERY = "DELETE FROM playlist WHERE id = ? AND owner_id = ?";
    private static final String IS_OWNED_BY_USER_QUERY = "SELECT 1 FROM playlist WHERE id = ? AND owner_id = ?";

    public List<Playlist> getAll() {
        List<Playlist> response = new ArrayList<>();

        this.performQuery(GET_ALL_PLAYLISTS_QUERY, resultSet -> {
            Playlist playlist = new Playlist();
            playlist.setId(resultSet.getInt("id"));
            playlist.setName(resultSet.getString("name"));
            playlist.setOwnerId(resultSet.getInt("owner_id"));

            response.add(playlist);
        });

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
        AtomicInteger duration = new AtomicInteger();

        this.performQuery(GET_TOTAL_DURATION_QUERY, resultSet -> duration.set(resultSet.getInt("duration")));

        return duration.get();
    }

    public boolean isOwnedByUser(int playlistId, int userId) {
        AtomicBoolean isOwner = new AtomicBoolean(false);

        // Due to the fact that this will only be called when the resultSet.next() is
        // successfully called in the method, it will always be true.
        this.performQuery(IS_OWNED_BY_USER_QUERY, resultSet -> isOwner.set(true), playlistId, userId);

        return isOwner.get();
    }
}
