package nl.fabianwennink.dea.controllers.tracks;

import nl.fabianwennink.dea.controllers.tracks.dto.TracksResponseDTO;
import nl.fabianwennink.dea.exceptions.UnauthorizedException;
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
        try {
            // Try to authenticate the user
            userService.authenticateToken(token);

            TracksResponseDTO tracksResponseDTO = new TracksResponseDTO();
            tracksResponseDTO.setTracks(trackService.getAllNotInPlaylist(forPlaylist));

            return Response.ok(tracksResponseDTO).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
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
