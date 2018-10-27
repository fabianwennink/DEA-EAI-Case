package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.TestConstants;
import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.dao.PlaylistDAO;
import nl.fabianwennink.dea.database.entities.Playlist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class PlaylistServiceTest extends TestConstants {

    @Mock private PlaylistDAO playlistDAO;
    private PlaylistService playlistService;

    private List<Playlist> playlistEntityList = new ArrayList<>();

    private static final String NEW_PLAYLIST_TITLE = "New Playlist Name";
    private static final int TOTAL_PLAYLIST_DURATION = 12345;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        playlistService = new PlaylistService();
        playlistService.setPlaylistDAO(playlistDAO);

        // Fill the playlistEntityList list with dummy entities.
        playlistEntityList.add(new Playlist());
        playlistEntityList.add(new Playlist());
    }

    @Test
    public void should_ReturnAllPlaylists() {
        Mockito.when(playlistDAO.getAll()).thenReturn(playlistEntityList);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        List<PlaylistDTO> playlists = playlistService.getAll(AUTHENTICATED_USER_ID);

        Assertions.assertNotNull(playlists);
        Assertions.assertTrue(playlists.size() > 0);
    }

    @Test
    public void should_ReturnTrue_IfPlaylistWasEdited() {
        Mockito.when(playlistDAO.editTitle(VALID_PLAYLIST_ID, NEW_PLAYLIST_TITLE, AUTHENTICATED_USER_ID)).thenReturn(true);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertTrue(playlistService.editTitle(VALID_PLAYLIST_ID, NEW_PLAYLIST_TITLE, AUTHENTICATED_USER_ID));
    }

    @Test
    public void should_ReturnFalse_IfPlaylistWasNotEdited() {
        Mockito.when(playlistDAO.editTitle(INVALID_PLAYLIST_ID, NEW_PLAYLIST_TITLE, AUTHENTICATED_USER_ID)).thenReturn(false);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertFalse(playlistService.editTitle(INVALID_PLAYLIST_ID, NEW_PLAYLIST_TITLE, AUTHENTICATED_USER_ID));
    }

    @Test
    public void should_ReturnTrue_IfPlaylistWasAdded() {
        Mockito.when(playlistDAO.create(Mockito.any(Playlist.class))).thenReturn(true);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertTrue(playlistService.add(new PlaylistDTO(), AUTHENTICATED_USER_ID));
    }

    @Test
    public void should_ReturnFalse_IfPlaylistWasAdded() {
        Mockito.when(playlistDAO.create(Mockito.any(Playlist.class))).thenReturn(false);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertFalse(playlistService.add(new PlaylistDTO(), AUTHENTICATED_USER_ID));
    }

    @Test
    public void should_ReturnTrue_IfPlaylistWasDeleted() {
        Mockito.when(playlistDAO.delete(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertTrue(playlistService.delete(VALID_PLAYLIST_ID, AUTHENTICATED_USER_ID));
    }

    @Test
    public void should_ReturnFalse_IfPlaylistWasNotDeleted() {
        Mockito.when(playlistDAO.delete(Mockito.anyInt(), Mockito.anyInt())).thenReturn(false);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertFalse(playlistService.delete(VALID_PLAYLIST_ID, AUTHENTICATED_USER_ID));
    }

    @Test
    public void should_ReturnTotalDurationOfPlaylists() {
        Mockito.when(playlistDAO.getTotalDuration()).thenReturn(TOTAL_PLAYLIST_DURATION);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertEquals(TOTAL_PLAYLIST_DURATION, playlistService.getTotalDuration());
    }

    @Test
    public void should_ReturnTrue_IfPlaylistOwnedByUser() {
        Mockito.when(playlistDAO.isOwnedByUser(Mockito.eq(AUTHENTICATED_USER_ID), Mockito.anyInt())).thenReturn(true);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertTrue(playlistService.isOwnedByUser(AUTHENTICATED_USER_ID, VALID_PLAYLIST_ID));
    }

    @Test
    public void should_ReturnFalse_IfPlaylistNotOwnedByUser() {
        Mockito.when(playlistDAO.isOwnedByUser(Mockito.eq(AUTHENTICATED_USER_ID), Mockito.anyInt())).thenReturn(false);

        // Test currently somewhat pointless since the service is missing any business logic.
        // Still tested in case of possible future changes in the method (addition of logic).

        Assertions.assertFalse(playlistService.isOwnedByUser(AUTHENTICATED_USER_ID, VALID_PLAYLIST_ID));
    }
}
