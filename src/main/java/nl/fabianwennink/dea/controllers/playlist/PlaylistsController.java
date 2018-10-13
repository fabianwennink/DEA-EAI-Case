package nl.fabianwennink.dea.controllers.playlist;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistResponseDTO;
import nl.fabianwennink.dea.exceptions.UnauthorizedException;
import nl.fabianwennink.dea.services.PlaylistService;
import nl.fabianwennink.dea.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/playlists")
public class PlaylistsController {

    private UserService userService;
    private PlaylistService playlistService;

    // Returns all playlists
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        try {
            int userID = userService.authenticateToken(token);
            PlaylistResponseDTO dto = createResponse(
                    playlistService.getAll(),
                    playlistService.getTotalDuration()
            );

            return Response.ok(dto).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    // Adds a new playlist
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlistDTO) {
        try {
            int userID = userService.authenticateToken(token);
            playlistDTO.setOwner(true);

            if(playlistService.addNew(playlistDTO)) {
                PlaylistResponseDTO dto = createResponse(
                        playlistService.getAll(),
                        playlistService.getTotalDuration()
                );

                return Response.ok(dto).build();
            }
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // Deletes a playlist
//    @DELETE
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/{playlist_id}")
//    public Response deletePlaylist(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token) {
//        if(userService.authenticateToken(token)) {
//            if(playlistId > 0) {
//                List<PlaylistDTO> playlists = playlistService.deleteWithId(playlistId);
//                PlaylistResponseDTO playlistResponseDTO = new PlaylistResponseDTO(playlists, 1);
//
//                return Response.ok(playlistResponseDTO).build();
//            }
//        }
//
//        return Response.status(Response.Status.BAD_REQUEST).build();
//    }

    // Updates a playlist
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/{playlist_id}")
//    public Response editPlaylist(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token, PlaylistDTO playlistDTO) {
//        if(userService.authenticateToken(token) != null) {
//            PlaylistResponseDTO dto = createResponse(playlistService.getAll(), playlistService.getTotalDuration());
//
//            return Response.ok(dto).build();
//        }
//
//        return Response.status(Response.Status.BAD_REQUEST).build();
//    }

    private PlaylistResponseDTO createResponse(List<PlaylistDTO> playlists, int length) {
        PlaylistResponseDTO playlistResponseDTO = new PlaylistResponseDTO();
        playlistResponseDTO.setPlaylists(playlists);
        playlistResponseDTO.setLength(length);

        return playlistResponseDTO;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }
}
