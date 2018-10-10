package nl.fabianwennink.dea.services;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.dao.PlaylistDAO;
import nl.fabianwennink.dea.database.entities.Playlist;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PlaylistService {

    private PlaylistDAO playlistDAO;

    public List<PlaylistDTO> getAll() {
        List<Playlist> playlists = playlistDAO.getAll();
        List<PlaylistDTO> playlistDTOs = new ArrayList<>();

        for(Playlist playlist : playlists) {
            // TODO naar mapper
            PlaylistDTO dto = new PlaylistDTO();
            dto.setId(playlist.getId());
            dto.setName(playlist.getName());
            dto.setOwner(playlist.isOwner());

            playlistDTOs.add(dto);
        }

        return playlistDTOs;
    }

    public List<PlaylistDTO> editTitle(int playlistId, String name) {
        List<Playlist> playlists = playlistDAO.editTitle(playlistId, name);
        List<PlaylistDTO> playlistDTOs = new ArrayList<>();

        // TODO naar mapper
        for(Playlist playlist : playlists) {
            PlaylistDTO dto = new PlaylistDTO();
            dto.setId(playlist.getId());
            dto.setName(playlist.getName());
            dto.setOwner(playlist.isOwner());

            playlistDTOs.add(dto);
        }

        return playlistDTOs;
    }

    public boolean addNew(PlaylistDTO playlistDTO) {

        // TODO naar mapper
        Playlist playlist = new Playlist();
        playlist.setId(playlistDTO.getId());
        playlist.setName(playlistDTO.getName());
        playlist.setOwner(playlistDTO.isOwner());

        return playlistDAO.create(playlist);
    }

    public int getTotalDuration() {
        return playlistDAO.getTotalDuration();
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }
}
