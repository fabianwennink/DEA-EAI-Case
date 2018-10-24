package nl.fabianwennink.dea.database.entities.mappers;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.entities.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMapper implements Mapper<Playlist, PlaylistDTO> {

    private static PlaylistMapper mapper;

    @Override
    public Playlist convertToEntity(PlaylistDTO dto, Object... args) {
        Playlist playlist = new Playlist();
        playlist.setId(dto.getId());
        playlist.setName(dto.getName());
        playlist.setOwnerId(-1);

        return playlist;
    }

    @Override
    public PlaylistDTO convertToDTO(Playlist entity, Object... args) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOwner(false);

        // If the first argument is an int, compare it to the owner ID
        if(isOwner(args[0], entity)) {
            dto.setOwner(true);
        }

        return dto;
    }

    @Override
    public List<Playlist> convertToEntity(List<PlaylistDTO> dtos, Object... args) {
        List<Playlist> playlists = new ArrayList<>();

        for(PlaylistDTO dto : dtos) {
            playlists.add(convertToEntity(dto, args));
        }

        return playlists;
    }

    @Override
    public List<PlaylistDTO> convertToDTO(List<Playlist> entities, Object... args) {
        List<PlaylistDTO> playlists = new ArrayList<>();

        for(Playlist playlist : entities) {
            playlists.add(convertToDTO(playlist, args));
        }

        return playlists;
    }

    public static PlaylistMapper getInstance() {
        if(mapper == null) {
            mapper = new PlaylistMapper();
        }

        return mapper;
    }

    private boolean isOwner(Object obj, Playlist playlist) {
        return (obj instanceof Integer && (Integer)obj == playlist.getOwnerId());
    }
}
