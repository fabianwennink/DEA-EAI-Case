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

    public int authenticateToken(String token) throws UnauthorizedException {
        User user = userDAO.verifyToken(token);

        if(user != null) {
            return user.getId();
        }

        throw new UnauthorizedException();
    }

    private String issueToken() {
        // UUID randomUUID() might not be the best choice, but it works for now.
        return UUID.randomUUID().toString();
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
