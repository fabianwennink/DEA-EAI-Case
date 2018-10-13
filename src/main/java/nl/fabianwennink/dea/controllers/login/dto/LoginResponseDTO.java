package nl.fabianwennink.dea.controllers.login.dto;

public class LoginResponseDTO {

    private String user;
    private String token;

    /**
     * Returns the name of a user.
     * @return The name of user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the name of a user.
     * @param user The name of a user.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Returns the authentication token of a user.
     * @return An authentication token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the authentication token of a user.
     * @param token An authentication token.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
