package nl.fabianwennink.dea.controllers.login.dto;

public class LoginRequestDTO {

    private String user;
    private String password;

    /**
     * Sets the username of the login request.
     * @param user Username of a user.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Sets the password of the login request.
     * @param password Password of a user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the username of the login request.
     * @return The username of a user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Returns the password of the login request.
     * @return The password of a user.
     */
    public String getPassword() {
        return password;
    }
}
