package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.dao.PlaylistDAO;
import nl.fabianwennink.dea.database.entities.Playlist;
import nl.fabianwennink.dea.database.entities.mappers.PlaylistMapper;

import javax.inject.Inject;
import java.util.List;

public class PlaylistService {

    private PlaylistDAO playlistDAO;


    public List<PlaylistDTO> getAll(int userId) {
        List<Playlist> playlists = playlistDAO.getAll();

        return PlaylistMapper.getInstance().convertToDTO(playlists, userId);
    }

    public boolean editTitle(int playlistId, String name, int userId) {
        return playlistDAO.editTitle(playlistId, name, userId);
    }

    public boolean add(PlaylistDTO playlistDTO, int userId) {
        Playlist playlist = PlaylistMapper.getInstance().convertToEntity(playlistDTO);
        playlist.setOwnerId(userId);

        return playlistDAO.create(playlist);
    }

    public boolean delete(int playlistId, int userId) {
        return playlistDAO.delete(playlistId, userId);
    }

    public int getTotalDuration() {
        return playlistDAO.getTotalDuration();
    }

    public boolean isOwnedByUser(int playlistId, int userId) {
        return playlistDAO.isOwnedByUser(playlistId, userId);
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }
}
