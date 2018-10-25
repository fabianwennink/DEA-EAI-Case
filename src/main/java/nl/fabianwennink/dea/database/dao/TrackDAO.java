package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.Track;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrackDAO extends BaseDAO {

    private static final String GET_TRACKS_IN_PLAYLIST_QUERY = "SELECT tra.*, pt.offlineAvailable FROM track tra INNER JOIN playlisttrack pt ON pt.track_id = tra.id WHERE pt.playlist_id = ?";
    private static final String GET_TRACKS_NOT_IN_PLAYLIST_QUERY = "SELECT * FROM track WHERE id NOT IN (SELECT track_id FROM playlisttrack WHERE playlist_id = ?);";
    private static final String DELETE_TRACK_FROM_PLAYLIST_QUERY = "DELETE plt FROM playlisttrack plt JOIN playlist pl ON plt.playlist_id = pl.id WHERE plt.track_id = ? AND plt.playlist_id = ? AND pl.owner_id = ?";
    private static final String ADD_TRACK_TO_PLAYLIST_QUERY = "INSERT INTO playlisttrack (track_id, playlist_id, offlineAvailable) VALUES (?, ?, ?)";

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
        return this.performUpdate(DELETE_TRACK_FROM_PLAYLIST_QUERY, trackId, playlistId, userId);
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
        return this.performUpdate(ADD_TRACK_TO_PLAYLIST_QUERY, track.getId(), playlistId, track.isOfflineAvailable());
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
        List<Map<String, Object>> results;

        if(reverse) {
            results = this.performQuery(GET_TRACKS_NOT_IN_PLAYLIST_QUERY, playlistId);
        } else {
            results = this.performQuery(GET_TRACKS_IN_PLAYLIST_QUERY, playlistId);
        }

        return getTrackFromResult(results);
    }

    /**
     * Returns a list of tracks that were fetched from the database.
     *
     * @param results A list containing the query results.
     *
     * @return A list of tracks.
     */
    private List<Track> getTrackFromResult(List<Map<String, Object>> results) {
        List<Track> tracks = new ArrayList<>();

        for(Map<String, Object> row : results) {
            Track track = new Track();
            track.setId((Integer)row.get("id"));
            track.setTitle((String)row.get("title"));
            track.setPerformer((String)row.get("performer"));
            track.setDuration((Integer)row.get("duration"));
            track.setAlbum((String)row.get("album"));
            track.setPlaycount((Integer)row.get("playcount"));
            track.setDescription((String)row.get("description"));

            // If offlineAvailable is 1 (TRUE), 0 (FALSE)
            if(row.get("offlineAvailable") != null) {
                track.setOfflineAvailable(((Boolean)row.get("offlineAvailable")));
            } else {
                track.setOfflineAvailable(false);
            }

            if(row.get("publicationDate") != null)
                track.setPublicationDate(row.get("publicationDate").toString());

            tracks.add(track);
        }

        return tracks;
    }
}
