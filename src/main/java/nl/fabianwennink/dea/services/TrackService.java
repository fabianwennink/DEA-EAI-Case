package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;

import java.util.List;

public class TrackService {

    public TrackDTO getTrackByIdFromPlaylist(List<TrackDTO> tracks, int id) {
        for(TrackDTO track : tracks) {
            if(track.getId() == id) {
                return track;
            }
        }

        return null;
    }
}
