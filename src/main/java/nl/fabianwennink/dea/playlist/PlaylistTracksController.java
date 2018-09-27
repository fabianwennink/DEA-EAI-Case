package nl.fabianwennink.dea.playlist;

import nl.fabianwennink.dea.Spotitube;
import nl.fabianwennink.dea.tracks.dto.TracksResponseDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/playlists/{id}/tracks")
public class PlaylistTracksController {

    @GET
    @Produces("application/json")
    public Response tracks(@PathParam("id") int playlistId, @QueryParam("token") String token) {
        if(token.equals(Spotitube.USER_TOKEN)) {
            TracksResponseDTO tracksResponseDTO = new TracksResponseDTO(Spotitube.getInstance().getTrackList());

            return Response.ok(tracksResponseDTO).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Produces("application/json")
    @Path("/{track_id}")
    public Response delete(@PathParam("id") int playlistId, @PathParam("track_id") int trackId, @QueryParam("token") String token) {

        System.out.println(trackId);

        // return playlist - id

        return null;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response post() {

        // return json

        return null;
    }
}
