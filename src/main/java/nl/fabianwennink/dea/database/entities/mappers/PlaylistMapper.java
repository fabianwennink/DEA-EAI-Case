package nl.fabianwennink.dea.database.entities.mappers;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.entities.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMapper implements Mapper<Playlist, PlaylistDTO> {

    private static PlaylistMapper mapper;

    @Override
    public Playlist convertToEntity(PlaylistDTO dto) {
        Playlist playlist = new Playlist();
        playlist.setId(dto.getId());
        playlist.setName(dto.getName());
        playlist.setOwnerId(-1);

        return playlist;
    }

    @Override
    public PlaylistDTO convertToDTO(Playlist entity) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOwner(false);

        return dto;
    }

    @Override
    public List<Playlist> convertToEntity(List<PlaylistDTO> dtos) {
        List<Playlist> playlists = new ArrayList<>();

        for(PlaylistDTO dto : dtos) {
            playlists.add(convertToEntity(dto));
        }

        return playlists;
    }

    @Override
    public List<PlaylistDTO> convertToDTO(List<Playlist> entities) {
        List<PlaylistDTO> playlists = new ArrayList<>();

        for(Playlist playlist : entities) {
            playlists.add(convertToDTO(playlist));
        }

        return playlists;
    }

    public static PlaylistMapper getInstance() {
        if(mapper == null) {
            mapper = new PlaylistMapper();
        }

        return mapper;
    }
}
