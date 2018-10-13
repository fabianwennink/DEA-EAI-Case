package nl.fabianwennink.dea;

import nl.fabianwennink.dea.controllers.login.LoginController;
import nl.fabianwennink.dea.controllers.login.dto.LoginRequestDTO;
import nl.fabianwennink.dea.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

public class LoginControllerTest {

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void tryAuthenticateCorrectUser() {
//        LoginController loginController = new LoginController();
//        loginController.setUserService(userService);
//
//        Mockito.when(userService.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
//
//        LoginRequestDTO requestDTO = new LoginRequestDTO();
//        requestDTO.setUser("fabian");
//        requestDTO.setPassword("TEST");
//
//        Response response = loginController.login(requestDTO);
//
//        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void tryAuthenticateIncorrectUser() {
//        LoginController loginController = new LoginController();
//        loginController.setUserService(userService);
//
//        Mockito.when(userService.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
//
//        LoginRequestDTO requestDTO = new LoginRequestDTO();
//        requestDTO.setUser("WrongUsername");
//        requestDTO.setPassword("CorrectUsername");
//
//        Response response = loginController.login(requestDTO);
//
//        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
