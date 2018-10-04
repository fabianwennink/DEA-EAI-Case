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

        PlaylistDTO dto1 = new PlaylistDTO();
        dto1.setId(1);
        dto1.setName("Test Metal");
        dto1.setOwner(false);
        dto1.setTracks(trackList);

        PlaylistDTO dto2 = new PlaylistDTO();
        dto2.setId(2);
        dto2.setName("My Great Playlist");
        dto2.setOwner(true);
        dto2.setTracks(trackList);

        playlists.add(dto1);
        playlists.add(dto2);
    }

    public boolean addNew(PlaylistDTO playlist) {
        if(getById(playlist.getId()) == null) {
            playlists.add(playlist);
            return true;
        }

        return false;
    }

    public List<PlaylistDTO> deleteWithId(int id) {
        playlists.removeIf(playlist -> playlist.getId() == id);

        return playlists;
    }

    public PlaylistDTO getById(int playlistId) {
        for(PlaylistDTO playlist : playlists) {
            if(playlist.getId() == playlistId) {
                return playlist;
            }
        }

        return null;
    }

    public List<PlaylistDTO> getAll() {
        return this.playlists;
    }
}
