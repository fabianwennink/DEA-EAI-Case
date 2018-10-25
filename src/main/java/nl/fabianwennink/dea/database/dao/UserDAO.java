package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.User;

public class UserDAO extends BaseDAO {

    private static final String LOGIN_USER_QUERY = "SELECT id, name from user WHERE username = ? AND password = ?";
    private static final String FETCH_USER_BY_TOKEN_QUERY = "SELECT id, name from user WHERE token = ?";
    private static final String STORE_USER_TOKEN_QUERY = "UPDATE user SET token = ? WHERE id = ?";

    public User getUser(String username, String password) {
        User user = new User();

        this.performQuery(LOGIN_USER_QUERY, resultSet -> {
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
        }, username, password);

        return user;
    }

    public User verifyToken(String token) {
        User user = new User();

        this.performQuery(FETCH_USER_BY_TOKEN_QUERY, resultSet -> {
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setToken(token);
        }, token);

        return user;
    }

    public boolean storeToken(User user) {
        return this.performUpdate(STORE_USER_TOKEN_QUERY, user.getToken(), user.getId());
    }
}
