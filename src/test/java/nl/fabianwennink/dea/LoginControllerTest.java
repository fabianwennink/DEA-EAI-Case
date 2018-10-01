package nl.fabianwennink.dea;

import nl.fabianwennink.dea.controllers.login.LoginController;
import nl.fabianwennink.dea.controllers.login.dto.LoginRequestDTO;
import nl.fabianwennink.dea.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

public class LoginControllerTest {

    @InjectMocks
    LoginController loginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TryAuthenticatedUser() {
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setUser(UserService.USERNAME);
        requestDTO.setPassword(UserService.PASSWORD);

        Response response = loginController.login(requestDTO);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void TryInvalidUserInfo() {
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setUser("Username");
        requestDTO.setPassword("Password");

        Response response = loginController.login(requestDTO);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
