package nl.fabianwennink.dea;

import nl.fabianwennink.dea.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.tracks.dto.TrackDTO;

import java.util.ArrayList;
import java.util.List;

public class Spotitube {

    private static Spotitube spotitube;

    public static final String USERNAME = "fabian";
    public static final String PASSWORD = "TEST";
    public static final String USER_TOKEN = "06e8dc08-fdc8-45c5-8915-b48f29689f78";

    private List<PlaylistDTO> playlists = new ArrayList<>();
    private List<TrackDTO> trackList = new ArrayList<>();

    private Spotitube() {
        trackList.add(new TrackDTO(1, "Ocean and a rock", "Lisa Test", 337, "Sea sew", -1, null, "BAM", true));
        trackList.add(new TrackDTO(2, "Ocean and a rock", "Lisa Hannigan", 337, "Sea sew", -1, null, null, false));

        playlists.add(new PlaylistDTO(1, "Test Metal", false, new ArrayList<>()));
        playlists.add(new PlaylistDTO(2, "Hard Rock", false, new ArrayList<>()));
        playlists.add(new PlaylistDTO(3, "My Great Playlist", true, new ArrayList<>()));
    }

    public List<PlaylistDTO> getPlaylists() {
        return this.playlists;
    }

    public List<TrackDTO> getTrackList() {
        return this.trackList;
    }

    public static Spotitube getInstance() {
        if(spotitube == null) {
            spotitube = new Spotitube();
        }

        return spotitube;
    }
}
