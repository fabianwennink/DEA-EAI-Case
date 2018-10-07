package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.dao.PlaylistDAO;

import java.util.List;

public class PlaylistService {

    // TODO why loop, just request single one
    public PlaylistDTO getById(int playlistId) {
        PlaylistDAO playlistDAO = new PlaylistDAO();
        List<PlaylistDTO> playlists = playlistDAO.getAll();

        for(PlaylistDTO playlist : playlists) {
            if(playlist.getId() == playlistId) {
                return playlist;
            }
        }

        return null;
    }

    public List<PlaylistDTO> getAll() {
        return new PlaylistDAO().getAll();
    }
}
