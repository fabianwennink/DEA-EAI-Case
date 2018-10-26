package nl.fabianwennink.dea;

import nl.fabianwennink.dea.controllers.playlisttracks.PlaylistTracksController;
import nl.fabianwennink.dea.exceptions.UnauthorizedException;
import nl.fabianwennink.dea.services.PlaylistService;
import nl.fabianwennink.dea.services.TrackService;
import nl.fabianwennink.dea.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

public class PlaylistTracksControllerTest {

    @Mock private TrackService trackService;
    @Mock private PlaylistService playlistService;
    @Mock private UserService userService;
    private PlaylistTracksController playlistTracksController;

    private static final int AUTHENTICATED_USER_ID = 1;
    private static final String TOKEN = "test-token";
    private static final String INCORRECT_TOKEN = "incorrect-made-up-token";
    private static final int VALID_PLAYLIST = 1;

    @BeforeEach
    public void setUp() throws UnauthorizedException {
        MockitoAnnotations.initMocks(this);

        playlistTracksController = new PlaylistTracksController();
        playlistTracksController.setPlaylistService(playlistService);
        playlistTracksController.setTrackService(trackService);
        playlistTracksController.setUserService(userService);

        // Mock userService.authenticateToken methods
        Mockito.when(userService.authenticateToken(Mockito.anyString())).thenThrow(UnauthorizedException.class);
        Mockito.when(userService.authenticateToken(Mockito.eq(TOKEN))).thenReturn(AUTHENTICATED_USER_ID);
    }

    @Test
    public void should_ReturnAllTracksInPlaylist_IfPlaylistExists() {
        Response response = playlistTracksController.getPlaylistTracks(VALID_PLAYLIST, TOKEN);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_ReturnUnauthorized_IfGetTracksUserUnauthorized() {
        Response response = playlistTracksController.getPlaylistTracks(VALID_PLAYLIST, INCORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_ReturnAllOtherTracks_IfTrackDeleted() {
        //Mockito.mock()
        Response response = playlistTracksController.deleteTrackFromPlaylist(1, 1, TOKEN);

    }
}
