package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.dao.PlaylistDAO;
import nl.fabianwennink.dea.database.entities.Playlist;
import nl.fabianwennink.dea.database.entities.mappers.PlaylistMapper;

import javax.inject.Inject;
import java.util.List;

public class PlaylistService {

    private UserService userService;
    private PlaylistDAO playlistDAO;

    public List<PlaylistDTO> getAll() {
        List<Playlist> playlists = playlistDAO.getAll();

        return PlaylistMapper.getInstance().convertToDTO(playlists);
    }

    public List<PlaylistDTO> editTitle(int playlistId, String name) {
        List<Playlist> playlists = playlistDAO.editTitle(playlistId, name);

        return PlaylistMapper.getInstance().convertToDTO(playlists);
    }

    public boolean addNew(PlaylistDTO playlistDTO) {
        Playlist playlist = PlaylistMapper.getInstance().convertToEntity(playlistDTO);

        return playlistDAO.create(playlist);
    }

    public int getTotalDuration() {
        return playlistDAO.getTotalDuration();
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }
}
