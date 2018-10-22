package nl.fabianwennink.dea;

import nl.fabianwennink.dea.controllers.playlist.PlaylistsController;
import nl.fabianwennink.dea.services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PlaylistControllerTest {

    @Mock private PlaylistService playlistService;
    private PlaylistsController playlistsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        playlistsController = new PlaylistsController();
    }



}
