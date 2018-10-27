package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.TestConstants;
import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;
import nl.fabianwennink.dea.database.dao.TrackDAO;
import nl.fabianwennink.dea.database.entities.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

public class TrackServiceTest extends TestConstants {

    @Mock private TrackDAO trackDAO;
    private TrackService trackService;

    private List<Track> trackEntityList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        trackService = new TrackService();
        trackService.setTrackDAO(trackDAO);

        // Fill the trackEntityList list with dummy entities.
        trackEntityList.add(new Track());
        trackEntityList.add(new Track());
    }

    @Test // TrackService.getAllByPlaylistId
    public void should_ReturnAllTracks_ByPlaylistID() {
        Mockito.when(trackDAO.getAllInPlaylist(VALID_PLAYLIST_ID)).thenReturn(trackEntityList);

        List<TrackDTO> tracks = trackService.getAllByPlaylistId(VALID_PLAYLIST_ID);

        // Test currently pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertNotNull(tracks);
        Assertions.assertTrue(tracks.size() > 0);
    }

    @Test // TrackService.getAllNotInPlaylist
    public void should_ReturnAllTracksNotInPlaylist_ByPlaylistID() {
        Mockito.when(trackDAO.getAllNotInPlaylist(VALID_PLAYLIST_ID)).thenReturn(trackEntityList);

        List<TrackDTO> tracks = trackService.getAllNotInPlaylist(VALID_PLAYLIST_ID);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertNotNull(tracks);
        Assertions.assertTrue(tracks.size() > 0);
    }

    @Test // TrackService.addTrackToPlaylist
    public void should_ReturnTrue_IfTrackWasAdded() {
        Mockito.when(trackDAO.addToPlaylist(Mockito.any(Track.class), Mockito.anyInt())).thenReturn(true);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertTrue(trackService.addTrackToPlaylist(new TrackDTO(), VALID_PLAYLIST_ID));
    }

    @Test // TrackService.addTrackToPlaylist
    public void should_ReturnFalse_IfTrackWasNotAdded() {
        Mockito.when(trackDAO.addToPlaylist(Mockito.any(Track.class), Mockito.anyInt())).thenReturn(false);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertFalse(trackService.addTrackToPlaylist(new TrackDTO(), INVALID_PLAYLIST_ID));
    }

    @Test // TrackService.deleteFromPlaylist
    public void should_ReturnTrue_IfTrackWasDeleted() {
        Mockito.when(trackDAO.deleteFromPlaylist(VALID_PLAYLIST_ID, VALID_TRACK_ID, AUTHENTICATED_USER_ID)).thenReturn(true);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertTrue(trackService.deleteFromPlaylist(VALID_PLAYLIST_ID, VALID_TRACK_ID, AUTHENTICATED_USER_ID));
    }

    @Test // TrackService.deleteFromPlaylist
    public void should_ReturnFalse_IfTrackWasNotDeleted() {
        Mockito.when(trackDAO.deleteFromPlaylist(INVALID_PLAYLIST_ID, INVALID_TRACK_ID, AUTHENTICATED_USER_ID)).thenReturn(false);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertFalse(trackService.deleteFromPlaylist(INVALID_PLAYLIST_ID, INVALID_TRACK_ID, AUTHENTICATED_USER_ID));
    }
}
