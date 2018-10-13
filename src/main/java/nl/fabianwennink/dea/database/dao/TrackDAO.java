package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO extends BaseDAO {

    private static final String GET_TRACKS_QUERY = "SELECT * FROM track";
    private static final String GET_TRACKS_BY_PLAYLIST_QUERY = "SELECT tra.* FROM track tra INNER JOIN playlisttrack pt ON pt.track_id = tra.id WHERE pt.playlist_id = ?";
    private static final String DELETE_TRACK_FROM_PLAYLIST_QUERY = "DELETE plt FROM playlisttrack plt JOIN playlist pl ON plt.playlist_id = pl.id WHERE plt.track_id = ? AND plt.playlist_id = ? AND pl.owner_id = ?";
    private static final String ADD_TRACK_TO_PLAYLIST_QUERY = "INSERT INTO playlisttrack (track_id, playlist_id) VALUES (?, ?)";

    public List<Track> getAll() {
        List<Track> tracks = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        try {
            connection = this.getConnection();

            statement = connection.prepareStatement(GET_TRACKS_QUERY);

            resultSet = statement.executeQuery();
            tracks = getTrackFromResult(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return tracks;
    }

    public List<Track> getAllByPlaylistId(int playlistId) {
        List<Track> tracks = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null ;

        try {
            connection = this.getConnection();

            statement = connection.prepareStatement(GET_TRACKS_BY_PLAYLIST_QUERY);
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
