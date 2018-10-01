package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;

import java.util.ArrayList;
import java.util.List;

public class PlaylistService {

    private List<PlaylistDTO> playlists = new ArrayList<>();

    public PlaylistService() {
        List<TrackDTO> trackList = new ArrayList<>();

        trackList.add(new TrackDTO(1, "Ocean and a rock", "Lisa Test", 337, "Sea sew", -1, null, "BAM", true));
        trackList.add(new TrackDTO(2, "Ocean and a rock", "Lisa Hannigan", 337, "Sea sew", -1, null, null, false));

        playlists.add(new PlaylistDTO(1, "Test Metal", false, trackList));
        playlists.add(new PlaylistDTO(2, "Hard Rock", false, trackList));
        playlists.add(new PlaylistDTO(3, "My Great Playlist", true, trackList));
    }

    public List<PlaylistDTO> getPlaylists() {
        return this.playlists;
    }

    public PlaylistDTO getPlaylistById(int playlistId) {
        for(PlaylistDTO playlist : playlists) {
            if(playlist.getId() == playlistId) {
                return playlist;
            }
        }

        return null;
    }
}
