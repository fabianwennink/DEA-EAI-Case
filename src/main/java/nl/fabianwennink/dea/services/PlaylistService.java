package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.dao.PlaylistDAO;

import java.util.List;

public class PlaylistService {

    public boolean addNew(PlaylistDTO playlist) {
        if(getById(playlist.getId()) == null) {
            PlaylistDAO playlists = new PlaylistDAO();



            //playlists.add(playlist);
            return true;
        }

        return false;
    }

    public List<PlaylistDTO> deleteWithId(int id) {
        //playlists.removeIf(playlist -> playlist.getId() == id);

        //return playlists;
        return null;
    }

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
        PlaylistDAO playlistDAO = new PlaylistDAO();

        return playlistDAO.getAll();
    }
}
