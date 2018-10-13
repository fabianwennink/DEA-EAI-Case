package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.database.dao.UserDAO;
import nl.fabianwennink.dea.database.entities.User;
import nl.fabianwennink.dea.database.entities.mappers.UserMapper;
import nl.fabianwennink.dea.exceptions.UnauthorizedException;

import javax.inject.Inject;
import java.util.UUID;

public class UserService {

    private UserDAO userDAO;

    /**
     * Tries to log in the given user from the LoginResponseDTO.
     * If the username:password combination is valid, a new authentication token will be generated and stored.
     *
     * @param username The username of a user.
     * @param password The password of a user.
     *
     * @return A User if the username:password combination was valid.
     * @throws UnauthorizedException If the username:password combination was invalid.
     */
    public LoginResponseDTO authenticate(String username, String password) throws UnauthorizedException {
        User user = userDAO.getUser(username, password);

        if(user != null) {

            // Generate a new token
            user.setToken(issueToken());

            // Try to store the token
            if(userDAO.storeToken(user)) {
                return UserMapper.getInstance().convertToDTO(user);
            }
        }

        throw new UnauthorizedException();
    }

    /**
     * Verifies the given token and returns a user if the token was valid.
     *
     * @param token An authentication token.
     *
     * @return A User if the token is valid.
     * @throws UnauthorizedException If the token was invalid.
     */
    public int authenticateToken(String token) throws UnauthorizedException {
        User user = userDAO.verifyToken(token);

        if(user != null) {
            return user.getId();
        }

        throw new UnauthorizedException();
    }

    /**
     * Generates a random authentication token.
     * Note: randomUUID() might not be the best choice, but it works for this application.
     *
     * @return A random authentication token.
     */
    private String issueToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Injects an instance of UserDAO.
     *
     * @param userDAO A UserDAO.
     */
    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
