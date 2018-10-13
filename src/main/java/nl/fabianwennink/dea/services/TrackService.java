package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;
import nl.fabianwennink.dea.database.dao.TrackDAO;
import nl.fabianwennink.dea.database.entities.Track;
import nl.fabianwennink.dea.database.entities.mappers.TrackMapper;

import javax.inject.Inject;
import java.util.List;

public class TrackService {

    private TrackDAO trackDAO;

    public List<TrackDTO> getAll() {
        List<Track> tracks = trackDAO.getAll();

        return TrackMapper.getInstance().convertToDTO(tracks);
    }

    public List<TrackDTO> getAllByPlaylistId(int playlistId) {
        List<Track> tracks = trackDAO.getAllByPlaylistId(playlistId);

        return TrackMapper.getInstance().convertToDTO(tracks);
    }

    public boolean addTrackToPlaylist(TrackDTO trackDTO, int playlistId) {
        Track track = TrackMapper.getInstance().convertToEntity(trackDTO);

        return trackDAO.addToPlaylist(track, playlistId);
    }

    public boolean deleteFromPlaylist(int playlistId, int trackId, int userId) {
        return trackDAO.deleteFromPlaylist(playlistId, trackId, userId);
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
