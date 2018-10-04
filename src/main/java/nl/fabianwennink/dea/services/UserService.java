package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.database.LoginDAO;
import nl.fabianwennink.dea.database.util.DatabaseProperties;

public class UserService {

    private static final String USER_TOKEN = "06e8dc08-fdc8-45c5-8915-b48f29689f78";

    public boolean authenticate(String username, String password) {
        LoginDAO loginDAO = new LoginDAO(new DatabaseProperties());
        return loginDAO.select(username, password) != null;
    }

    public boolean tokenMatches(String token) {
        return token.equals(USER_TOKEN);
    }

    public String getToken() {
        return USER_TOKEN;
    }
}
