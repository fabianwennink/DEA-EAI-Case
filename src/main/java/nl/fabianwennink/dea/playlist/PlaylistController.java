package nl.fabianwennink.dea.playlist;

import nl.fabianwennink.dea.Spotitube;
import nl.fabianwennink.dea.playlist.dto.PlaylistReponseDTO;
import nl.fabianwennink.dea.tracks.dto.TracksResponseDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/playlists")
public class PlaylistController {

    @GET
    @Produces("application/json")
    public Response playlists(@QueryParam("token") String token) {
        if(token.equals(Spotitube.USER_TOKEN)) {
            PlaylistReponseDTO playlistReponseDTO = new PlaylistReponseDTO(Spotitube.getInstance().getPlaylists(), 103974);

            return Response.ok(playlistReponseDTO).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Produces("application/json")
    @Path("/{id}")
    public Response delete(@PathParam("id") int playlistId, @QueryParam("token") String token) {
        return null;
    }

    @POST
    @Produces("application/json")
    public Response post(@QueryParam("token") String token) {
        return null;
    }

    @PUT
    @Produces("application/json")
    @Path("/{id}")
    public Response edit(@PathParam("id") int playlistId, @QueryParam("token") String token) {
        return null;
    }
}
