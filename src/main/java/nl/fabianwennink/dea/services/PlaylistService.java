package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.dao.PlaylistDAO;
import nl.fabianwennink.dea.database.entities.Playlist;
import nl.fabianwennink.dea.database.entities.mappers.PlaylistMapper;

import javax.inject.Inject;
import java.util.List;

public class PlaylistService {

    private PlaylistDAO playlistDAO;

    /**
     * Returns a list of all available playlists (as DTOs), with the 'owner' property
     * of a playlist set based on the given user ID.
     *
     * @param userId The ID of a user.
     *
     * @return A list of playlist DTOs.
     */
    public List<PlaylistDTO> getAll(int userId) {
        List<Playlist> playlists = playlistDAO.getAll();

        return PlaylistMapper.getInstance().convertToDTO(playlists, userId);
    }

    /**
     * Edits the title of a playlist. The given user should own the playlist in
     * order to update the title.
     *
     * @param playlistId The ID of a playlist.
     * @param name The new title of a playlist.
     * @param userId The ID of a user.
     *
     * @return TRUE if the title was changed, FALSE if it wasn't.
     */
    public boolean editTitle(int playlistId, String name, int userId) {
        return playlistDAO.editTitle(playlistId, name, userId);
    }

    /**
     * Creates a new playlist, with the given user as owner.
     *
     * @param playlistDTO The playlist as DTO.
     * @param userId The ID of a user.
     *
     * @return TRUE if the playlist was created, FALSE if it wasn't.
     */
    public boolean add(PlaylistDTO playlistDTO, int userId) {
        Playlist playlist = PlaylistMapper.getInstance().convertToEntity(playlistDTO);
        playlist.setOwnerId(userId);

        return playlistDAO.create(playlist);
    }

    /**
     * Deletes a playlist. The given user should own the playlist in
     * order to delete the playlist.
     *
     * @param playlistId The ID of a playlist.
     * @param userId The ID of a user.
     *
     * @return TRUE if the playlist was deleted, FALSE if it wasn't.
     */
    public boolean delete(int playlistId, int userId) {
        return playlistDAO.delete(playlistId, userId);
    }

    /**
     * Returns the total duration of all tracks in all the playlists.
     *
     * @return The summed up duration of all tracks.
     */
    public int getTotalDuration() {
        return playlistDAO.getTotalDuration();
    }

    /**
     * Checks if the given user owns the given playlist.
     *
     * @param playlistId The ID of a playlist.
     * @param userId The ID of a user.
     *
     * @return TRUE if the user owns the playlist, FALSE if it doesn't.
     */
    public boolean isOwnedByUser(int playlistId, int userId) {
        return playlistDAO.isOwnedByUser(playlistId, userId);
    }

    /**
     * Injects an instance of PlaylistDAO.
     *
     * @param playlistDAO A PlaylistDAO.
     */
    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }
}
