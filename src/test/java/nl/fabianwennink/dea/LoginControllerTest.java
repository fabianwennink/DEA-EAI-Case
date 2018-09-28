package nl.fabianwennink.dea;

import nl.fabianwennink.dea.login.LoginController;
import nl.fabianwennink.dea.login.dto.LoginRequestDTO;
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
        requestDTO.setUser(Spotitube.USERNAME);
        requestDTO.setPassword(Spotitube.PASSWORD);

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
