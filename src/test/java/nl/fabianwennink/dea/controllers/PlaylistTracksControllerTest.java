package nl.fabianwennink.dea.controllers;

import nl.fabianwennink.dea.TestConstants;
import nl.fabianwennink.dea.controllers.playlisttracks.PlaylistTracksController;
import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;
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

public class PlaylistTracksControllerTest extends TestConstants {

    @Mock private TrackService trackService;
    @Mock private PlaylistService playlistService;
    @Mock private UserService userService;
    private PlaylistTracksController playlistTracksController;

    @BeforeEach
    public void setUp() throws UnauthorizedException {
        MockitoAnnotations.initMocks(this);

        playlistTracksController = new PlaylistTracksController();
        playlistTracksController.setPlaylistService(playlistService);
        playlistTracksController.setTrackService(trackService);
        playlistTracksController.setUserService(userService);

        // Mock userService.authenticateToken methods
        Mockito.when(userService.authenticateToken(Mockito.anyString())).thenThrow(UnauthorizedException.class);
        Mockito.when(userService.authenticateToken(Mockito.eq(CORRECT_TOKEN))).thenReturn(AUTHENTICATED_USER_ID);
    }

    @Test // playlistTracksController.getPlaylistTracks
    public void should_ReturnAllTracksInPlaylist_IfPlaylistExists() {
        Response response = playlistTracksController.getPlaylistTracks(VALID_PLAYLIST_ID, CORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.getPlaylistTracks
    public void should_ReturnUnauthorized_IfPlaylistWasInvalidGetTracks() {
        Response response = playlistTracksController.getPlaylistTracks(INVALID_PLAYLIST_ID, CORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.getPlaylistTracks
    public void should_ReturnUnauthorized_IfGetTracksUserUnauthorized() {
        Response response = playlistTracksController.getPlaylistTracks(VALID_PLAYLIST_ID, INCORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.deleteTrackFromPlaylist
    public void should_ReturnAllOtherTracks_IfTrackDeleted() {
        this.setMockDeleteFromPlaylist(true);
        this.setMockOwnedByUser(true);

        Response response = playlistTracksController.deleteTrackFromPlaylist(VALID_PLAYLIST_ID, VALID_TRACK_ID, CORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.deleteTrackFromPlaylist
    public void should_ReturnBadRequest_IfTrackWasNotDeletedCauseDeleteInServiceFail() {
        this.setMockDeleteFromPlaylist(false);
        this.setMockOwnedByUser(true);

        Response response = playlistTracksController.deleteTrackFromPlaylist(VALID_PLAYLIST_ID, VALID_TRACK_ID, CORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.deleteTrackFromPlaylist
    public void should_ReturnBadRequest_IfTrackWasNotDeletedCauseOwnedByCheckInServiceFail() {
        this.setMockDeleteFromPlaylist(true);
        this.setMockOwnedByUser(false);

        Response response = playlistTracksController.deleteTrackFromPlaylist(VALID_PLAYLIST_ID, VALID_TRACK_ID, CORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.deleteTrackFromPlaylist
    public void should_ReturnBadRequest_IfTrackWasNotDeletedCauseInvalidPlaylist() {
        this.setMockDeleteFromPlaylist(true);
        this.setMockOwnedByUser(true);

        Response response = playlistTracksController.deleteTrackFromPlaylist(INVALID_PLAYLIST_ID, VALID_TRACK_ID, CORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.deleteTrackFromPlaylist
    public void should_ReturnBadRequest_IfTrackWasNotDeletedCauseInvalidTrack() {
        this.setMockDeleteFromPlaylist(true);
        this.setMockOwnedByUser(true);

        Response response = playlistTracksController.deleteTrackFromPlaylist(VALID_PLAYLIST_ID, INVALID_TRACK_ID, CORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.deleteTrackFromPlaylist
    public void should_ReturnUnauthorized_IfUserUnauthorizedInDeleteTrack() {
        Response response = playlistTracksController.deleteTrackFromPlaylist(VALID_PLAYLIST_ID, VALID_TRACK_ID, INCORRECT_TOKEN);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.addTrackToPlaylist
    public void should_ReturnAllTracks_IfTrackWasAdded() {
        this.setMockAddToPlaylist(true);
        this.setMockOwnedByUser(true);

        Response response = playlistTracksController.addTrackToPlaylist(VALID_PLAYLIST_ID, CORRECT_TOKEN, new TrackDTO());

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.addTrackToPlaylist
    public void should_ReturnBadRequest_IfPlaylistInvalid() {
        this.setMockAddToPlaylist(true);
        this.setMockOwnedByUser(true);

        Response response = playlistTracksController.addTrackToPlaylist(INVALID_PLAYLIST_ID, CORRECT_TOKEN, new TrackDTO());

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.addTrackToPlaylist
    public void should_ReturnBadRequest_IfTrackNotAdded() {
        this.setMockAddToPlaylist(false);
        this.setMockOwnedByUser(true);

        Response response = playlistTracksController.addTrackToPlaylist(VALID_PLAYLIST_ID, CORRECT_TOKEN, new TrackDTO());

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.addTrackToPlaylist
    public void should_ReturnBadRequest_IfPlaylistNotOwnedByUserAddTrack() {
        this.setMockAddToPlaylist(true);
        this.setMockOwnedByUser(false);

        Response response = playlistTracksController.addTrackToPlaylist(VALID_PLAYLIST_ID, CORRECT_TOKEN, new TrackDTO());

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test // playlistTracksController.addTrackToPlaylist
    public void should_ReturnUnauthorized_IfUserNotAuthorizedAddTrack() {
        Response response = playlistTracksController.addTrackToPlaylist(VALID_PLAYLIST_ID, INCORRECT_TOKEN, new TrackDTO());

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    private void setMockDeleteFromPlaylist(boolean status) {
        // Mock trackService.deleteFromPlaylist, TRUE = track has been 'deleted', FALSE = track has not been 'deleted'.
        Mockito.when(trackService.deleteFromPlaylist(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(status);
    }

    private void setMockAddToPlaylist(boolean status) {
        // Mock trackService.addTrackToPlaylist, TRUE = track has been 'added', FALSE = track has not been 'added'.
        Mockito.when(trackService.addTrackToPlaylist(Mockito.any(TrackDTO.class), Mockito.anyInt())).thenReturn(status);
    }

    private void setMockOwnedByUser(boolean status) {
        // Mock playlistService.isOwnedByUser, TRUE = playlist is owned by user, FALSE = playlist is not owned by user.
        Mockito.when(playlistService.isOwnedByUser(Mockito.anyInt(), Mockito.anyInt())).thenReturn(status);
    }
}
