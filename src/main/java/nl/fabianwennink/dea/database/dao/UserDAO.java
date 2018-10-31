package nl.fabianwennink.dea.database.dao;

import nl.fabianwennink.dea.database.entities.User;

public class UserDAO extends BaseDAO {

    private static final String LOGIN_USER_QUERY = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password"; // id, name
    private static final String FETCH_USER_BY_TOKEN_QUERY = "SELECT u from User u WHERE u.token = :token"; // id, name

    public User getSingle(String username, String password) {
        User user = (User) entityManager.createQuery(LOGIN_USER_QUERY)
                .setParameter("username", username).setParameter("password", password)
                .getSingleResult();

        // if the name is not set, the user wasn't properly fetched aka doesn't exist.
        return (user.getName() != null) ? user : null;
    }

    public User verifyToken(String token) {
        User user = (User) entityManager.createQuery(FETCH_USER_BY_TOKEN_QUERY)
                .setParameter("token", token)
                .getSingleResult();

        // if the name is not set, the user wasn't properly fetched aka doesn't exist.
        return (user.getName() != null) ? user : null;
    }

    public boolean persist(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch(Exception e) {
            return false;
        }

        return true;
    }
}
