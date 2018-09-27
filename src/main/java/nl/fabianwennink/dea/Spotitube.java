package nl.fabianwennink.dea;

import nl.fabianwennink.dea.playlist.Playlist;
import nl.fabianwennink.dea.tracks.Track;

import java.util.ArrayList;
import java.util.List;

public class Spotitube {

    private static Spotitube spotitube;

    public static final String USERNAME = "fabian";
    public static final String PASSWORD = "TEST";
    public static final String USER_TOKEN = "06e8dc08-fdc8-45c5-8915-b48f29689f78";

    private List<Playlist> playlists = new ArrayList<>();
    private List<Track> trackList = new ArrayList<>();

    private Spotitube() {
        trackList.add(new Track(5, "Ocean and a rock", "Lisa Test", 337, "Sea sew", -1, null, "BAM", true));
        trackList.add(new Track(3, "Ocean and a rock", "Lisa Hannigan", 337, "Sea sew", -1, null, null, false));

        playlists.add(new Playlist(1, "Test Metal", false, new ArrayList<>()));
        playlists.add(new Playlist(2, "Hard Rock", false, new ArrayList<>()));
        playlists.add(new Playlist(3, "My Great Playlist", true, new ArrayList<>()));
    }

    public List<Playlist> getPlaylists() {
        return this.playlists;
    }

    public List<Track> getTrackList() {
        return this.trackList;
    }

    public static Spotitube getInstance() {
        if(spotitube == null) {
            spotitube = new Spotitube();
        }

        return spotitube;
    }
}
