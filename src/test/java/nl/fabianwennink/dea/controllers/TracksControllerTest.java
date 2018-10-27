package nl.fabianwennink.dea.controllers;

import nl.fabianwennink.dea.TestConstants;
import nl.fabianwennink.dea.controllers.tracks.TracksController;
import nl.fabianwennink.dea.exceptions.UnauthorizedException;
import nl.fabianwennink.dea.services.TrackService;
import nl.fabianwennink.dea.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class TracksControllerTest extends TestConstants {

    @Mock private UserService userService;
    @Mock private TrackService trackService;
    private TracksController tracksController;

    @BeforeEach
    public void setUp() throws UnauthorizedException {
        MockitoAnnotations.initMocks(this);

        tracksController = new TracksController();
        tracksController.setTrackService(trackService);
        tracksController.setUserService(userService);

        // Mock userService.authenticateToken methods
        Mockito.when(userService.authenticateToken(Mockito.anyString())).thenThrow(UnauthorizedException.class);
        Mockito.when(userService.authenticateToken(Mockito.eq(CORRECT_TOKEN))).thenReturn(AUTHENTICATED_USER_ID);

        // Mock trackService.getAllNotInPlaylist method
        Mockito.when(trackService.getAllNotInPlaylist(Mockito.anyInt())).thenReturn(new ArrayList<>());
    }

    @Test
    public void should_ReturnTracks_IfPlaylistExists() {
        Response response = tracksController.tracks(CORRECT_TOKEN, VALID_PLAYLIST_ID);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_ReturnBadRequest_IfPlaylistIsInvalid() {
        Response response = tracksController.tracks(CORRECT_TOKEN, INVALID_PLAYLIST_ID);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_ReturnUnauthorized_IfTokenInvalid() {
        Response response = tracksController.tracks(INCORRECT_TOKEN, VALID_PLAYLIST_ID);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
