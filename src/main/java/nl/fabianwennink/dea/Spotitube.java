package nl.fabianwennink.dea;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Spotitube {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response index() {
        return Response.ok("Hello World\r\n\r\nhttp://ci.icaprojecten.nl/spotitube/").build();
    }
}
