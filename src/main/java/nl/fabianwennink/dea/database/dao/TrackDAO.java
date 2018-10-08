package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO extends BaseDAO {

    private static final String GET_TRACKS_BY_PLAYLIST_QUERY = "SELECT * FROM tracks JOIN playlisttracks pt WHERE pt.playlist_id = ?";

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
            while (resultSet.next()) {
                Track track = new Track();
                track.setId(resultSet.getInt("id"));
                track.setTitle(resultSet.getString("title"));
                track.setPerformer(resultSet.getString("performer"));
                track.setDuration(resultSet.getInt("duration"));
                track.setAlbum(resultSet.getString("album"));
                track.setPlayCount(resultSet.getInt("playcount"));
                track.setPublicationDate(resultSet.getDate("publicationCate").toString());
                track.setDescription(resultSet.getString("description"));
                track.setOfflineAvailable(resultSet.getBoolean("offlineAvailable"));

                tracks.add(track);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.close(connection, statement, resultSet);
        }

        return tracks;
    }
}
