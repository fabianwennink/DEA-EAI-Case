package nl.fabianwennink.dea.tracks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.ws.Response;

@Path("/tracks")
public class TrackController {

    @GET
    @Produces("application/json")
    public Response tracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int forPlaylist) {
        return null;
    }
}
