package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;

import javax.sound.midi.Track;
import java.util.Iterator;
import java.util.List;

public class TrackService {

    public TrackService() {

    }

    public TrackDTO getTrackByIdFromPlaylist(List<TrackDTO> tracks, int id) {
        for(TrackDTO track : tracks) {
            if(track.getId() == id) {
                return track;
            }
        }

        return null;
    }

    public List<TrackDTO> deleteTrackWithId(List<TrackDTO> tracks, int id) {
        Iterator<TrackDTO> track = tracks.iterator();

        while(track.hasNext()) {
            TrackDTO tr = track.next();

            if(tr.getId() == id) {
                track.remove();
            }
        }

        return tracks;
    }
}
