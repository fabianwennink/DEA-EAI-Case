package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.User;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

public class UserDAO extends BaseDAO {

    private static final String LOGIN_USER_QUERY = "SELECT id, name from user WHERE username = ? AND password = ?";
    private static final String FETCH_USER_BY_TOKEN_QUERY = "SELECT id, name from user WHERE token = ?";
    private static final String STORE_USER_TOKEN_QUERY = "UPDATE user SET token = ? WHERE id = ?";

    public User getUser(String username, String password) {
        User user = new User();

        List<Map<String, Object>> results = performQuery(LOGIN_USER_QUERY, username, password);
        for(Map<String, Object> row : results) {
            user.setId((Integer)row.get("id"));
            user.setName((String)row.get("name"));
        }

        return user;
    }

    public User verifyToken(String token) {
        User user = new User();

        List<Map<String, Object>> results = performQuery(FETCH_USER_BY_TOKEN_QUERY, token);

        for(Map<String, Object> row : results) {
            user.setId((Integer)row.get("id"));
            user.setName((String)row.get("name"));
            user.setToken(token);
        }

        return user;
    }

    public boolean storeToken(User user) {
        return this.performUpdate(STORE_USER_TOKEN_QUERY, user.getToken(), user.getId());
    }
}
