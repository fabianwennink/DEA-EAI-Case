package nl.fabianwennink.dea.services;

public class UserService {

    public static final String USERNAME = "fabian";
    public static final String PASSWORD = "TEST";
    private static final String USER_TOKEN = "06e8dc08-fdc8-45c5-8915-b48f29689f78";

    public boolean shouldAuthenticate(String username, String password) {
        return username.equalsIgnoreCase(USERNAME) && password.equals(PASSWORD);
    }

    public boolean tokenMatches(String token) {
        return USER_TOKEN.equals(token);
    }

    public String getToken() {
        return USER_TOKEN;
    }
}
