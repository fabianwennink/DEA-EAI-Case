package nl.fabianwennink.dea.controllers.playlisttracks;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;
import nl.fabianwennink.dea.controllers.tracks.dto.TracksResponseDTO;
import nl.fabianwennink.dea.database.dao.TrackDAO;
import nl.fabianwennink.dea.database.entities.Track;
import nl.fabianwennink.dea.services.PlaylistService;
import nl.fabianwennink.dea.services.TrackService;
import nl.fabianwennink.dea.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/playlists/{playlist_id}/tracks")
public class PlaylistTracksController {

    private UserService userService;
    private PlaylistService playlistService;
    private TrackService trackService;

    // Returns the tracks of a playlist
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylistTracks(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token) {
        if(userService.tokenMatches(token)) {
            List<TrackDTO> tracks = trackService.getAllByPlaylistId(playlistId);

            if(tracks.size() > 0) {
                TracksResponseDTO tracksResponseDTO = new TracksResponseDTO(tracks);

                return Response.ok(tracksResponseDTO).build();
            }
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // Deletes a track from a playlist
//    @DELETE
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/{track_id}")
//    public Response deleteTrackFromPlaylist(@PathParam("playlist_id") int playlistId, @PathParam("track_id") int trackId, @QueryParam("token") String token) {//
//        return Response.status(Response.Status.BAD_REQUEST).build();
//    }

    // Adds a track to a playlist
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/tracks/{track_id}")
//    public Response addTrackToPlaylist(@PathParam("playlist_id") int playlistId, @PathParam("track_id") int trackId, @QueryParam("token") String token) {
//        return Response.status(Response.Status.BAD_REQUEST).build();
//    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }
}
