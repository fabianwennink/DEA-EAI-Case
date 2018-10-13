package nl.fabianwennink.dea.controllers.login;

import nl.fabianwennink.dea.controllers.login.dto.LoginRequestDTO;
import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.exceptions.UnauthorizedException;
import nl.fabianwennink.dea.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    private UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO) {
        try {
            return Response.ok(
                    userService.authenticate(loginRequestDTO.getUser(), loginRequestDTO.getPassword())
            ).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
