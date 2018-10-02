package nl.fabianwennink.dea.controllers.playlist;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistResponseDTO;
import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;
import nl.fabianwennink.dea.controllers.tracks.dto.TracksResponseDTO;
import nl.fabianwennink.dea.services.PlaylistService;
import nl.fabianwennink.dea.services.TrackService;
import nl.fabianwennink.dea.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/playlists")
public class PlaylistsController {

    @Inject
    private UserService userService;
    @Inject
    private PlaylistService playlistService;
    @Inject
    private TrackService trackService;

    // Returns all playlists
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        if(userService.tokenMatches(token)) {
            PlaylistResponseDTO playlistResponseDTO = new PlaylistResponseDTO(playlistService.getAll(), 103974);

            return Response.ok(playlistResponseDTO).build();
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
        if(userService.tokenMatches(token)) {
            if(playlistId > 0) {

            }
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
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
        if(userService.tokenMatches(token)) {
            PlaylistDTO playlist = playlistService.getById(playlistId);
            TracksResponseDTO tracksResponseDTO = new TracksResponseDTO(playlist.getTracks());

            return Response.ok(tracksResponseDTO).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // Deletes a track from a playlist
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlist_id}/tracks/{track_id}")
    public Response deleteTrackFromPlaylist(@PathParam("playlist_id") int playlistId, @PathParam("track_id") int trackId, @QueryParam("token") String token) {
        if(userService.tokenMatches(token)) {
            PlaylistDTO playlist = playlistService.getById(playlistId);
            TracksResponseDTO tracksResponseDTO = new TracksResponseDTO(playlist.getTracks());

            List<TrackDTO> tracks = trackService.deleteTrackWithId(tracksResponseDTO.getTracks(), trackId);

            // set tracks for playlist

            return Response.ok().build();
        }

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
