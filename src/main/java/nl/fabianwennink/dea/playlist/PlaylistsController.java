package nl.fabianwennink.dea.playlist;

import nl.fabianwennink.dea.Spotitube;
import nl.fabianwennink.dea.playlist.dto.PlaylistReponseDTO;
import nl.fabianwennink.dea.tracks.dto.TracksResponseDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/playlists")
public class PlaylistsController {

    // Returns all playlists
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        if(token.equals(Spotitube.USER_TOKEN)) {
            PlaylistReponseDTO playlistReponseDTO = new PlaylistReponseDTO(Spotitube.getInstance().getPlaylists(), 103974);

            return Response.ok(playlistReponseDTO).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // Adds a new playlist
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token) {
        return null;
    }

    // Deletes a playlist
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlist_id}")
    public Response deletePlaylist(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token) {
        return null;
    }

    // Updates a playlist
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlist_id}")
    public Response editPlaylist(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token) {
        return null;
    }

    // Returns the tracks of a playlist
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlist_id}/tracks")
    public Response getPlaylistTracks(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token) {
        if(token.equals(Spotitube.USER_TOKEN)) {
            TracksResponseDTO tracksResponseDTO = new TracksResponseDTO(Spotitube.getInstance().getTrackList());

            return Response.ok(tracksResponseDTO).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // Deletes a track from a playlist
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlist_id}/tracks/{track_id}")
    public Response deleteTrackFromPlaylist(@PathParam("playlist_id") int playlistId, @PathParam("track_id") int trackId, @QueryParam("token") String token) {

        System.out.println(trackId);

        // return playlist - id

        return null;
    }

    // Adds a track to a playlist
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{playlist_id}/tracks/{track_id}")
    public Response addTrackToPlaylist(@PathParam("playlist_id") int playlistId, @PathParam("track_id") int trackId, @QueryParam("token") String token) {

        // return json

        return null;
    }
}
