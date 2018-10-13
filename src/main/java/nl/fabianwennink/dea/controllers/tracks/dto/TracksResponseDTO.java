package nl.fabianwennink.dea.controllers.tracks.dto;

import java.util.List;

public class TracksResponseDTO {

    private List<TrackDTO> tracks;

    /* GETTERS AND SETTERS REQUIRED FOR API TO WORK */

    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }
}
