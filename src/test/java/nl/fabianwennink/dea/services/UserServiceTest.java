package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.TestConstants;
import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.database.dao.UserDAO;
import nl.fabianwennink.dea.database.entities.User;
import nl.fabianwennink.dea.exceptions.UnauthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserServiceTest extends TestConstants {

    @Mock private UserDAO userDAO;
    private UserService userService;

    private User userEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService();
        userService.setUserDAO(userDAO);

        // Create the dummy User entity
        userEntity = new User();
        userEntity.setId(AUTHENTICATED_USER_ID);
        userEntity.setName(LOGIN_USERNAME);
        userEntity.setUsername(LOGIN_USERNAME);
        userEntity.setToken(CORRECT_TOKEN);

        Mockito.when(userDAO.getUser(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        Mockito.when(userDAO.getUser(Mockito.eq(LOGIN_USERNAME), Mockito.eq(LOGIN_PASSWORD))).thenReturn(userEntity);
        Mockito.when(userDAO.storeToken(userEntity)).thenReturn(true);
        Mockito.when(userDAO.verifyToken(Mockito.anyString())).thenReturn(null);
        Mockito.when(userDAO.verifyToken(Mockito.eq(CORRECT_TOKEN))).thenReturn(userEntity);
    }

    @Test
    public void should_ReturnLoginResponse_IfUserAuthorized() throws UnauthorizedException {
        LoginResponseDTO loginResponseDTO = userService.authenticate(LOGIN_USERNAME, LOGIN_PASSWORD);

        // Token should not be the same as the 'fetched' dummy user.
        Assertions.assertNotEquals(CORRECT_TOKEN, loginResponseDTO.getToken());
    }

    @Test
    public void should_ThrowsUnauthorizedException_IfUserNotAuthorized() {
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            userService.authenticate("INCORRECT_USERNAME", "INCORRECT_PASSWORD");
        });
    }

    @Test
    public void should_ReturnUserId_IfUserTokenAuthenticated() throws UnauthorizedException {
        int userId = userService.authenticateToken(CORRECT_TOKEN);

        Assertions.assertEquals(userId, userEntity.getId());
    }

    @Test
    public void should_ThrowsUnauthorizedException_IfUserTokenNotAuthorized() {
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            userService.authenticateToken(INCORRECT_TOKEN);
        });
    }
}
