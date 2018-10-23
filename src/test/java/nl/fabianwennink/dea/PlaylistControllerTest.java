package nl.fabianwennink.dea;

import nl.fabianwennink.dea.controllers.playlist.PlaylistsController;
import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.exceptions.UnauthorizedException;
import nl.fabianwennink.dea.services.PlaylistService;
import nl.fabianwennink.dea.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class PlaylistControllerTest {

    @Mock private PlaylistService playlistService;
    @Mock private UserService userService;
    private PlaylistsController playlistsController;

    private static final int AUTHENTICATED_USER_ID = 1;
    private static final String TOKEN = "test-token";

    @BeforeEach
    public void setUp() throws UnauthorizedException {
        MockitoAnnotations.initMocks(this);

        playlistsController = new PlaylistsController();
        playlistsController.setPlaylistService(playlistService);
        playlistsController.setUserService(userService);

        // Mock userService.authenticateToken methods
        Mockito.when(userService.authenticateToken(Mockito.anyString())).thenThrow(UnauthorizedException.class);
        Mockito.when(userService.authenticateToken(Mockito.eq(TOKEN))).thenReturn(AUTHENTICATED_USER_ID);

        // Mock playlistService methods, just to be sure the response at least contains values
        Mockito.when(playlistService.getAll(AUTHENTICATED_USER_ID)).thenReturn(new ArrayList<>());
        Mockito.when(playlistService.getTotalDuration()).thenReturn(0);
    }

    @Test // PlaylistController.getPlaylists
    public void should_ReturnAllPlaylists_IfUserAuthenticated() {
        Response response = playlistsController.getPlaylists(TOKEN);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test // PlaylistController.getPlaylists
    public void should_ReturnUnauthorized_IfUserUnauthorized() {
        Response response = playlistsController.getPlaylists("incorrect-made-up-token");

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_AddPlaylistAndReturnAll_IfPlaylistDoesNotExist() {
        // Mock playlistService.add method, always returns true (playlist does not exist).
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(AUTHENTICATED_USER_ID))).thenReturn(true);

        Response response = playlistsController.addPlaylist(TOKEN, new PlaylistDTO());

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_ReturnBadRequest_IfPlaylistAlreadyExists() {
        // Mock playlistService.add method, always returns true (playlist already exist).
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(AUTHENTICATED_USER_ID))).thenReturn(false);

        Response response = playlistsController.addPlaylist(TOKEN, new PlaylistDTO());

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
