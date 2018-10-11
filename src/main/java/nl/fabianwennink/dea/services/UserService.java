package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.database.dao.UserDAO;
import nl.fabianwennink.dea.database.entities.User;
import nl.fabianwennink.dea.database.entities.mappers.UserMapper;

public class UserService {

    private static final String USER_TOKEN = "06e8dc08-fdc8-45c5-8";

    public LoginResponseDTO authenticate(String username, String password) {
        User user = new UserDAO().getUser(username, password);

        if(user != null) {
            return UserMapper.getInstance().convertToDTO(user);
        }

        return null;
    }

    // TODO Remove hard-coded token, store somewhere idk
    public boolean tokenMatches(String token) {
        return token.equals(USER_TOKEN);
    }

    public String getToken() {
        return USER_TOKEN;
    }
}
