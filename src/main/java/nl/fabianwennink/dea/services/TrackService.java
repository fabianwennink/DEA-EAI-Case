package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;
import nl.fabianwennink.dea.database.dao.TrackDAO;
import nl.fabianwennink.dea.database.entities.Track;

import javax.inject.Inject;
import java.util.List;

public class TrackService {

    private TrackDAO trackDAO;

    public List<TrackDTO> getAllByPlaylistId(int playlistId) {
        List<Track> tracks = trackDAO.getAllByPlaylistId(playlistId);

    }

//    public TrackDTO getTrackByIdFromPlaylist(List<TrackDTO> tracks, int id) {
//        for(TrackDTO track : tracks) {
//            if(track.getId() == id) {
//                return track;
//            }
//        }
//
//        return null;
//    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
