package nl.fabianwennink.dea.controllers.tracks;

import nl.fabianwennink.dea.services.TrackService;
import nl.fabianwennink.dea.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TracksController {

    private UserService userService;
    private TrackService trackService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response tracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int forPlaylist) {
        if(userService.tokenMatches(token)) {

        }

        return null;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }
}
