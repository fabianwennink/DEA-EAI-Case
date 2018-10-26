package nl.fabianwennink.dea.controllers;

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

    private PlaylistDTO dto;

    private static final int AUTHENTICATED_USER_ID = 1;
    private static final String TOKEN = "test-token";
    private static final String INCORRECT_TOKEN = "incorrect-made-up-token";

    private static final int VALID_PLAYLIST = 1;
    private static final int INVALID_PLAYLIST = -1;

    @BeforeEach
    public void setUp() throws UnauthorizedException {
        MockitoAnnotations.initMocks(this);

        playlistsController = new PlaylistsController();
        playlistsController.setPlaylistService(playlistService);
        playlistsController.setUserService(userService);

        // PlaylistDTO
        dto = new PlaylistDTO();
        dto.setId(1);
        dto.setName("NewTitle");
        dto.setOwner(true);
        dto.setTracks(new ArrayList<>());

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
        Response response = playlistsController.getPlaylists(INCORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test // PlaylistController.addPlaylists
    public void should_AddPlaylistAndReturnAll_IfPlaylistDoesNotExist() {
        // Mock playlistService.add method, always returns true (playlist does not exist).
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(AUTHENTICATED_USER_ID))).thenReturn(true);

        Response response = playlistsController.addPlaylist(TOKEN, dto);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test // PlaylistController.addPlaylists
    public void should_ReturnBadRequest_IfPlaylistAlreadyExists() {
        // Mock playlistService.add method, always returns true (playlist already exist).
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(AUTHENTICATED_USER_ID))).thenReturn(false);

        Response response = playlistsController.addPlaylist(TOKEN, dto);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // PlaylistController.addPlaylists
    public void should_ReturnUnauthorized_IfUserUnauthorizedInAddPlaylist() {
        // Mock playlistService.add method, always returns true (playlist already exist).
        Mockito.when(playlistService.add(Mockito.any(PlaylistDTO.class), Mockito.eq(AUTHENTICATED_USER_ID))).thenReturn(false);

        Response response = playlistsController.addPlaylist(INCORRECT_TOKEN, dto);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test // PlaylistController.deletePlaylist
    public void should_DeletePlaylistAndReturnAll_IfPlaylistDeleted() {
        Mockito.when(playlistService.delete(VALID_PLAYLIST, AUTHENTICATED_USER_ID)).thenReturn(true);

        Response response = playlistsController.deletePlaylist(VALID_PLAYLIST, TOKEN);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test // PlaylistController.deletePlaylist
    public void should_ReturnBadRequest_IfPlaylistIdIsInvalid() {
        Mockito.when(playlistService.delete(INVALID_PLAYLIST, AUTHENTICATED_USER_ID)).thenReturn(true);

        Response response = playlistsController.deletePlaylist(INVALID_PLAYLIST, TOKEN);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // PlaylistController.deletePlaylist
    public void should_ReturnBadRequest_IfPlaylistNotDeleted() {
        Mockito.when(playlistService.delete(VALID_PLAYLIST, AUTHENTICATED_USER_ID)).thenReturn(false);

        Response response = playlistsController.deletePlaylist(VALID_PLAYLIST, TOKEN);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // PlaylistController.deletePlaylist
    public void should_ReturnUnauthorized_IfUserUnauthorizedInDeletePlaylist() {
        Mockito.when(playlistService.delete(VALID_PLAYLIST, AUTHENTICATED_USER_ID)).thenReturn(false);

        Response response = playlistsController.deletePlaylist(VALID_PLAYLIST, INCORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_EditPlaylistAndReturnAll_IfPlaylistWasEdited() {
        Mockito.when(playlistService.editTitle(VALID_PLAYLIST, "NewTitle", AUTHENTICATED_USER_ID)).thenReturn(true);

        Response response = playlistsController.editPlaylist(VALID_PLAYLIST, TOKEN, dto);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_ReturnBadRequest_IfEditPlaylistIdIsZero() {
        Mockito.when(playlistService.editTitle(INVALID_PLAYLIST, "NewTitle", AUTHENTICATED_USER_ID)).thenReturn(true);

        Response response = playlistsController.editPlaylist(INVALID_PLAYLIST, TOKEN, dto);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_ReturnBadRequest_IfPlaylistWasNotEdited() {
        Mockito.when(playlistService.editTitle(VALID_PLAYLIST, "NewTitle", AUTHENTICATED_USER_ID)).thenReturn(false);

        Response response = playlistsController.editPlaylist(VALID_PLAYLIST, TOKEN, dto);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_ReturnUnauthorized_IfUserUnauthorizedInEditPlaylist() {
        Mockito.when(playlistService.editTitle(INVALID_PLAYLIST, "NewTitle", AUTHENTICATED_USER_ID)).thenReturn(true);

        Response response = playlistsController.editPlaylist(VALID_PLAYLIST, INCORRECT_TOKEN, dto);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
