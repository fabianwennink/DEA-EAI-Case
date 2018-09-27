package nl.fabianwennink.dea.login;

import nl.fabianwennink.dea.Spotitube;
import nl.fabianwennink.dea.login.dto.LoginRequestDTO;
import nl.fabianwennink.dea.login.dto.LoginResponseDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO) {
        if(shouldAuthenticate(loginRequestDTO)) {
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setToken(Spotitube.USER_TOKEN);
            loginResponseDTO.setUser(loginRequestDTO.getUser());

            return Response.ok(loginResponseDTO).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    private boolean shouldAuthenticate(LoginRequestDTO loginRequestDTO) {
        return loginRequestDTO.getUser().equalsIgnoreCase(Spotitube.USERNAME) && loginRequestDTO.getPassword().equals(Spotitube.PASSWORD);
    }
}
