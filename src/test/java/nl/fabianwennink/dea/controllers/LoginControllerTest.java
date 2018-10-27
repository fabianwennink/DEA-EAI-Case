package nl.fabianwennink.dea.controllers;

import nl.fabianwennink.dea.TestConstants;
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

public class LoginControllerTest extends TestConstants {

    @Mock private UserService userService;
    private LoginController loginController;

    private LoginRequestDTO requestDTO;
    private LoginResponseDTO responseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        loginController = new LoginController();

        // Set the user service
        loginController.setUserService(userService);

        // Create the login request
        requestDTO = new LoginRequestDTO();
        requestDTO.setUser(LOGIN_USERNAME);
        requestDTO.setPassword(LOGIN_PASSWORD);

        // Create the response
        responseDTO = new LoginResponseDTO();
        responseDTO.setUser(LOGIN_USERNAME);
        responseDTO.setToken(CORRECT_TOKEN);
    }

    @Test
    public void should_ReturnAuthenticatedUser_IfUserAuthenticated() throws UnauthorizedException {
        Mockito.when(userService.authenticate(requestDTO.getUser(), requestDTO.getPassword())).thenReturn(responseDTO);

        Response response = loginController.login(requestDTO);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void should_ReturnUnauthorizedResponse_IfUserUnauthenticated() throws UnauthorizedException {
        Mockito.when(userService.authenticate(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(UnauthorizedException.class);

        Response response = loginController.login(requestDTO);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
