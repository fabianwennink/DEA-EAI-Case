package nl.fabianwennink.dea.controllers.login;

import nl.fabianwennink.dea.controllers.login.dto.LoginRequestDTO;
import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.database.LoginDAO;
import nl.fabianwennink.dea.database.util.DatabaseProperties;
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
        if(userService.authenticate(loginRequestDTO.getUser(), loginRequestDTO.getPassword())) {

            LoginDAO loginDAO = new LoginDAO(new DatabaseProperties());
            LoginResponseDTO loginResponseDTO = loginDAO.select(loginRequestDTO.getUser(), loginRequestDTO.getPassword());
//
//            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
//            loginResponseDTO.setToken(userService.getToken());
//            loginResponseDTO.setUser(loginRequestDTO.getUser());

            return Response.ok(loginResponseDTO).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
