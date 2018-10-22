package nl.fabianwennink.dea;

import nl.fabianwennink.dea.controllers.login.LoginController;
import nl.fabianwennink.dea.controllers.login.dto.LoginRequestDTO;
import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.exceptions.UnauthorizedException;
import nl.fabianwennink.dea.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

public class LoginControllerTest {

    @Mock private UserService userService;
    private LoginController loginController;

    private LoginRequestDTO requestDTO;
    private LoginResponseDTO responseDTO;

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "test";
    private static final String TOKEN = "9516d318-4739-4164-960f-2da965d07c3d";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        loginController = new LoginController();

        // Set the user service
        loginController.setUserService(userService);

        // Create the login request
        requestDTO = new LoginRequestDTO();
        requestDTO.setUser(USERNAME);
        requestDTO.setPassword(PASSWORD);

        // Create the response
        responseDTO = new LoginResponseDTO();
        responseDTO.setUser(USERNAME);
        responseDTO.setToken(TOKEN);
    }

    @Test
    public void tryAuthenticateCorrectUser() throws UnauthorizedException {
        Mockito.when(userService.authenticate(requestDTO.getUser(), requestDTO.getPassword())).thenReturn(responseDTO);

        Response response = loginController.login(requestDTO);

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void tryAuthenticateIncorrectUser() throws UnauthorizedException {
        Mockito.when(userService.authenticate(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(UnauthorizedException.class);

        Response response = loginController.login(requestDTO);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
