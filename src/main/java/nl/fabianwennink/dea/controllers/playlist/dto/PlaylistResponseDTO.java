package nl.fabianwennink.dea.controllers.playlist.dto;

import java.util.List;

public class PlaylistResponseDTO {

    private List<PlaylistDTO> playlists;
    private int length;

    public PlaylistResponseDTO(List<PlaylistDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    /* GETTERS AND SETTERS REQUIRED FOR API TO WORK */

    public List<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
