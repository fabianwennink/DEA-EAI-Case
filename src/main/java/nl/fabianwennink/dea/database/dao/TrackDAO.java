package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO extends BaseDAO {

    private static final String GET_TRACKS_IN_PLAYLIST_QUERY = "SELECT tra.* FROM track tra INNER JOIN playlisttrack pt ON pt.track_id = tra.id WHERE pt.playlist_id = ?";
    private static final String GET_TRACKS_NOT_IN_PLAYLIST_QUERY = "SELECT * FROM track WHERE id NOT IN (SELECT track_id FROM playlisttrack WHERE playlist_id = ?)";
    private static final String DELETE_TRACK_FROM_PLAYLIST_QUERY = "DELETE plt FROM playlisttrack plt JOIN playlist pl ON plt.playlist_id = pl.id WHERE plt.track_id = ? AND plt.playlist_id = ? AND pl.owner_id = ?";
    private static final String ADD_TRACK_TO_PLAYLIST_QUERY = "INSERT INTO playlisttrack (track_id, playlist_id) VALUES (?, ?)";

    /**
     * Returns a list of tracks that are added to the given playlist.
     *
     * @param playlistId The ID of the playlist.
     *
     * @return A list of tracks.
     */
    public List<Track> getAllInPlaylist(int playlistId) {
        return getByPlaylistId(playlistId, false);
    }

    /**
     * Returns a list of tracks that have not yet been added to the given playlist.
     *
     * @param playlistId The ID of the playlist.
     *
     * @return A list of tracks.
     */
    public List<Track> getAllNotInPlaylist(int playlistId) {
        return getByPlaylistId(playlistId, true);
    }

    /**
     * Deletes a track from the given playlist.
     *
     * @param playlistId The ID of the playlist from which the track will be deleted.
     * @param trackId The ID of the track that will be deleted.
     * @param userId The ID of the user who owns the playlist.
     *
     * @return TRUE if the track was deleted, FALSE if it wasn't.
     */
    public boolean deleteFromPlaylist(int playlistId, int trackId, int userId) {
        boolean deleted = false;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        try {
            connection = this.getConnection();

            statement = connection.prepareStatement(DELETE_TRACK_FROM_PLAYLIST_QUERY);
            statement.setInt(1, trackId);
            statement.setInt(2, playlistId);
            statement.setInt(3, userId);

            if(statement.executeUpdate() > 0) {
                deleted = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return deleted;
    }

    /**
     * Adds a track to the given playlist.
     *
     * @param track The Track that will be added to the playlist.
     * @param playlistId The ID of the playlist to which the track will be added.
     *
     * @return TRUE if the track was added, FALSE if it wasn't.
     */
    public boolean addToPlaylist(Track track, int playlistId) {
        boolean stored = false;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        try {
            connection = this.getConnection();

            statement = connection.prepareStatement(ADD_TRACK_TO_PLAYLIST_QUERY);
            statement.setInt(1, track.getId());
            statement.setInt(2, playlistId);

            if(statement.executeUpdate() > 0) {
                stored = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return stored;
    }

    /**
     * Returns a list of tracks that have either been added to a playlist, or can still be added to one.
     * Which one of the two is returned, is determined based on the value of the 'reverse' parameter.
     *
     * @param playlistId The ID of the playlist.
     * @param reverse If TRUE, tracks not in the given playlist will be
     *                returned, FALSE will return tracks already added.
     *
     * @return A list of tracks.
     */
    private List<Track> getByPlaylistId(int playlistId, boolean reverse) {
        List<Track> tracks = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        try {
            connection = this.getConnection();

            if(reverse) {
                statement = connection.prepareStatement(GET_TRACKS_NOT_IN_PLAYLIST_QUERY);
            } else {
                statement = connection.prepareStatement(GET_TRACKS_IN_PLAYLIST_QUERY);
            }

            statement.setInt(1, playlistId);

            resultSet = statement.executeQuery();
            tracks = getTrackFromResult(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return tracks;
    }

    /**
     * Returns a list of tracks that were fetched from the database.
     *
     * @param resultSet The ResultSet containing the query results.
     *
     * @return A list of tracks.
     * @throws SQLException If there was a problem with the ResultSet.
     */
    private List<Track> getTrackFromResult(ResultSet resultSet) throws SQLException {
        List<Track> tracks = new ArrayList<>();

        while (resultSet.next()) {
            Track track = new Track();
            track.setId(resultSet.getInt("id"));
            track.setTitle(resultSet.getString("title"));
            track.setPerformer(resultSet.getString("performer"));
            track.setDuration(resultSet.getInt("duration"));
            track.setAlbum(resultSet.getString("album"));
            track.setPlaycount(resultSet.getInt("playcount"));
            track.setDescription(resultSet.getString("description"));
            track.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));

            if(resultSet.getTimestamp("publicationDate") != null)
                track.setPublicationDate(resultSet.getTimestamp("publicationDate").toString());

            tracks.add(track);
        }

        return tracks;
    }
}
