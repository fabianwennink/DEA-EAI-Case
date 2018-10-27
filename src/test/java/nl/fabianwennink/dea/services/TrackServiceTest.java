package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.database.dao.TrackDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TrackServiceTest {

    @Mock private TrackDAO trackDAO;
    private TrackService trackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        trackService = new TrackService();
        trackService.setTrackDAO(trackDAO);
    }

    @Test // TrackService.getAllByPlaylistId
    @Disabled
    public void should_ReturnAllTracks_ByPlaylistID() {

    }

    @Test // TrackService.getAllNotInPlaylist
    @Disabled
    public void should_ReturnAllTracksNotInPlaylist_ByPlaylistID() {

    }

    @Test // TrackService.addTrackToPlaylist
    @Disabled
    public void should_ReturnAllTracks_IfTrackAdded() {

    }

    @Test // TrackService.deleteFromPlaylist
    @Disabled
    public void should_ReturnAllTracks_IfTrackDeleted() {

    }
}
