package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;
import nl.fabianwennink.dea.database.dao.TrackDAO;
import nl.fabianwennink.dea.database.entities.Track;
import nl.fabianwennink.dea.database.entities.mappers.TrackMapper;

import javax.inject.Inject;
import java.util.List;

public class TrackService {

    private TrackDAO trackDAO;

    /**
     * Returns a list of all tracks of a given playlist.
     *
     * @param playlistId The ID of a playlist.
     *
     * @return A list of all tracks in a playlist.
     */
    public List<TrackDTO> getAllByPlaylistId(int playlistId) {
        List<Track> tracks = trackDAO.getAllInPlaylist(playlistId);

        return TrackMapper.getInstance().convertToDTO(tracks);
    }

    /**
     * Returns a list of all the available tracks that are not yet in a playlist.
     *
     * @return A list of all tracks not in the given playlist.
     */
    public List<TrackDTO> getAllNotInPlaylist(int playlistId) {
        List<Track> tracks = trackDAO.getAllNotInPlaylist(playlistId);

        return TrackMapper.getInstance().convertToDTO(tracks);
    }

    /**
     * Adds a track to the given playlist.
     *
     * @param trackDTO A track as DTO.
     * @param playlistId The ID of a playlist.
     *
     * @return TRUE if the track was added to the playlist, FALSE if it wasn't.
     */
    public boolean addTrackToPlaylist(TrackDTO trackDTO, int playlistId) {
        Track track = TrackMapper.getInstance().convertToEntity(trackDTO);

        return trackDAO.addToPlaylist(track, playlistId);
    }

    /**
     * Deletes a track from the given playlist.
     *
     * @param playlistId The ID of a playlist from which the track should be deleted.
     * @param trackId The ID of the track which should be deleted.
     * @param userId The ID of a user who should own the given playlist.
     *
     * @return TRUE if the track was deleted from the playlist, FALSE if it wasn't.
     */
    public boolean deleteFromPlaylist(int playlistId, int trackId, int userId) {
        return trackDAO.deleteFromPlaylist(playlistId, trackId, userId);
    }

    /**
     * Injects an instance of TrackDAO.
     *
     * @param trackDAO A TrackDAO.
     */
    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
