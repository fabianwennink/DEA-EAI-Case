package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.database.dao.UserDAO;
import nl.fabianwennink.dea.database.entities.User;
import nl.fabianwennink.dea.database.entities.mappers.UserMapper;

import javax.inject.Inject;

public class UserService {

    // TODO Remove hard-coded token, store somewhere idk
    private static final String USER_TOKEN = "06e8dc08-fdc8-45c5-8";

    private UserDAO userDAO;

    public LoginResponseDTO authenticate(String username, String password) {
        User user = userDAO.getUser(username, password);
        if(user != null) {
            return UserMapper.getInstance().convertToDTO(user);
        }

        return null;
    }

    public LoginResponseDTO authenticate(String token) {
        User user = userDAO.getUser(token);
        if(user != null) {
            return UserMapper.getInstance().convertToDTO(user);
        }

        return null;
    }

    public boolean tokenMatches(String token) {
        return token.equals(USER_TOKEN);
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
