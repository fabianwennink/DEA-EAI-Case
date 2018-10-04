package nl.fabianwennink.dea;

import nl.fabianwennink.dea.controllers.playlist.PlaylistsController;
import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistResponseDTO;
import nl.fabianwennink.dea.services.PlaylistService;
import nl.fabianwennink.dea.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class PlaylistControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private PlaylistService playlistService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void tryToRequestPlaylists() {
        PlaylistsController playlistController = new PlaylistsController();
        playlistController.setUserService(userService);
        playlistController.setPlaylistService(playlistService);

//        Response response = playlistController.getPlaylists(userService.getToken());
//        PlaylistResponseDTO dto = (PlaylistResponseDTO)response.getEntity();
//
//        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//        Assertions.assertNotNull(dto);
    }
}
