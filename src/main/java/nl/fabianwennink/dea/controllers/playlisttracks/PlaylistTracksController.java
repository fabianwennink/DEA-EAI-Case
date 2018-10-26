package nl.fabianwennink.dea.controllers.playlisttracks;

import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;
import nl.fabianwennink.dea.controllers.tracks.dto.TracksResponseDTO;
import nl.fabianwennink.dea.exceptions.UnauthorizedException;
import nl.fabianwennink.dea.services.PlaylistService;
import nl.fabianwennink.dea.services.TrackService;
import nl.fabianwennink.dea.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists/{playlist_id}/tracks")
public class PlaylistTracksController {

    private UserService userService;
    private TrackService trackService;
    private PlaylistService playlistService;

    // Returns the tracks of a playlist
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylistTracks(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token) {
        try {
            // Try to authenticate the user
            userService.authenticateToken(token);

            if(playlistId > 0) {
                TracksResponseDTO tracksResponseDTO = new TracksResponseDTO();
                tracksResponseDTO.setTracks(trackService.getAllByPlaylistId(playlistId));

                return Response.ok(tracksResponseDTO).build();
            }
        } catch(UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // Deletes a track from a playlist
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{track_id}")
    public Response deleteTrackFromPlaylist(@PathParam("playlist_id") int playlistId, @PathParam("track_id") int trackId, @QueryParam("token") String token) {
        try {
            // Try to authenticate the user
            int userID = userService.authenticateToken(token);

            if(playlistId > 0 && trackId > 0 && playlistService.isOwnedByUser(playlistId, userID)
                    && trackService.deleteFromPlaylist(playlistId, trackId, userID)) {
                TracksResponseDTO tracksResponseDTO = new TracksResponseDTO();
                tracksResponseDTO.setTracks(trackService.getAllByPlaylistId(playlistId));

                return Response.ok(tracksResponseDTO).build();
            }
        } catch(UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // Adds a track to a playlist
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token, TrackDTO trackDTO) {
        try {
            // Try to authenticate the user
            int userID = userService.authenticateToken(token);

            if(playlistId > 0 && playlistService.isOwnedByUser(playlistId, userID)
                    && trackService.addTrackToPlaylist(trackDTO, playlistId)) {
                TracksResponseDTO tracksResponseDTO = new TracksResponseDTO();
                tracksResponseDTO.setTracks(trackService.getAllByPlaylistId(playlistId));

                return Response.ok(tracksResponseDTO).build();
            }
        } catch(UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }
}
