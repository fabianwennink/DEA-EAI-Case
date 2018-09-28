package nl.fabianwennink.dea;

import nl.fabianwennink.dea.login.LoginController;
import nl.fabianwennink.dea.login.dto.LoginRequestDTO;
import nl.fabianwennink.dea.playlist.PlaylistsController;
import nl.fabianwennink.dea.playlist.dto.PlaylistResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

public class PlaylistControllerTest {

    @InjectMocks
    PlaylistsController playlistsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void possibleToRequestPlaylists() {
        Response response = playlistsController.getPlaylists(Spotitube.USER_TOKEN);
        PlaylistResponseDTO dto = (PlaylistResponseDTO)response.getEntity();

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertNotNull(dto);
    }
}
