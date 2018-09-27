package nl.fabianwennink.dea.playlist.dto;

import nl.fabianwennink.dea.playlist.Playlist;

import java.util.List;

public class PlaylistReponseDTO {

    private List<Playlist> playlists;
    private int length;

    public PlaylistReponseDTO(List<Playlist> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    /* GETTERS AND SETTERS REQUIRED FOR API TO WORK */

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
