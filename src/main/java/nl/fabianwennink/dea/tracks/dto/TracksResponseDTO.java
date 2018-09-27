package nl.fabianwennink.dea.tracks.dto;

import nl.fabianwennink.dea.tracks.Track;

import java.util.List;

public class TracksResponseDTO {

    private List<Track> tracks;

    public TracksResponseDTO(List<Track> tracks) {
        this.tracks = tracks;
    }

    /* GETTERS AND SETTERS REQUIRED FOR API TO WORK */

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
