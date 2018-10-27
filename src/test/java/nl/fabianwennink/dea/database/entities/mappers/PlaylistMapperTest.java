package nl.fabianwennink.dea.database.entities.mappers;

import nl.fabianwennink.dea.controllers.playlist.dto.PlaylistDTO;
import nl.fabianwennink.dea.database.entities.Playlist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMapperTest {

    private List<Playlist> playlists = new ArrayList<>();
    private List<PlaylistDTO> dtos = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        // Fill the playlists list with dummy data
        playlists.add(createPlaylist(1, "Test Playlist", 3));
        playlists.add(createPlaylist(2, "Some Playlist", 2));
        playlists.add(createPlaylist(3, "Another Playlist", 1));

        // Fill the dtos list with dummy data
        playlists.forEach(playlist -> dtos.add(createResponseDTO(playlist, true)));
    }

    @Test
    public void should_ReturnPlaylistDTO_IfMappedCorrectly() {
        Playlist playlist = playlists.get(0);
        PlaylistDTO response = PlaylistMapper.getInstance().convertToDTO(playlist);

        checkDTO(response, playlist, false);
    }

    @Test
    public void should_ReturnPlaylistDTO_IfMappedCorrectlyIncludingOwner() {
        Playlist playlist = playlists.get(0);
        PlaylistDTO response = PlaylistMapper.getInstance().convertToDTO(playlist, playlist.getOwnerId());

        checkDTO(response, playlist, true);
    }

    @Test
    public void should_ReturnPlaylistEntity_IfMappedCorrectly() {
        PlaylistDTO dto = dtos.get(0);
        Playlist response = PlaylistMapper.getInstance().convertToEntity(dto);

        checkEntity(response, dto);
    }

    @Test
    public void should_ReturnPlaylistDTOs_IfMappedCorrectly() {
        List<PlaylistDTO> mappedDTOs = PlaylistMapper.getInstance().convertToDTO(playlists);

        Assertions.assertEquals(mappedDTOs.size(), playlists.size());

        for(int i = 0; i < mappedDTOs.size(); i++) {
            checkDTO(mappedDTOs.get(i), playlists.get(i), false);
        }
    }

    @Test
    public void should_ReturnPlaylistEntities_IfMappedCorrectly() {
        List<Playlist> mappedEntities = PlaylistMapper.getInstance().convertToEntity(dtos);

        Assertions.assertEquals(mappedEntities.size(), dtos.size());

        for(int i = 0; i < mappedEntities.size(); i++) {
            checkEntity(mappedEntities.get(i), dtos.get(i));
        }
    }

    private Playlist createPlaylist(int id, String name, int ownerId) {
        Playlist playlist = new Playlist();
        playlist.setId(id);
        playlist.setName(name);
        playlist.setOwnerId(ownerId);

        return playlist;
    }

    private PlaylistDTO createResponseDTO(Playlist playlist, boolean owner) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(playlist.getId());
        dto.setName(playlist.getName());
        dto.setOwner(owner);

        return dto;
    }

    private void checkDTO(PlaylistDTO dto, Playlist entity, boolean ownerTrue) {
        Assertions.assertEquals(dto.getId(), entity.getId());
        Assertions.assertEquals(dto.getName(), entity.getName());

        if(ownerTrue) {
            Assertions.assertTrue(dto.isOwner());
        } else {
            Assertions.assertFalse(dto.isOwner());
        }
    }

    private void checkEntity(Playlist entity, PlaylistDTO dto) {
        Assertions.assertEquals(entity.getId(), dto.getId());
        Assertions.assertEquals(entity.getName(), dto.getName());
        Assertions.assertEquals(entity.getOwnerId(), -1);
    }
}
