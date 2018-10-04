package nl.fabianwennink.dea.controllers.playlist.dto;

import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;

import java.util.List;

public class PlaylistDTO {

    private int id;
    private String name;
    private boolean owner;
    private List<TrackDTO> tracks;

    /* GETTERS AND SETTERS REQUIRED FOR API TO WORK */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }
}
