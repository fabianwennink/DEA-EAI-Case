package nl.fabianwennink.dea.database.entities.mappers;

import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;
import nl.fabianwennink.dea.database.entities.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TrackMapperTest {

    private List<Track> tracks = new ArrayList<>();
    private List<TrackDTO> dtos = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        // Fill the tracks list with dummy data
        tracks.add(createTrack(1, "Test Track #1", "Me", 1000, "Test Album", 200, "01-01-2000", "Nothing", false));
        tracks.add(createTrack(2, "Test Track #2", "You", 2000, "Some Album", 300, "02-02-2000", "Something", true));

        // Fill the dtos list with dummy data
        tracks.forEach(track -> dtos.add(createTrackDTO(track)));
    }

    @Test
    public void should_ReturnTrackDTO_IfMappedCorrectly() {
        Track track = tracks.get(0);
        TrackDTO response = TrackMapper.getInstance().convertToDTO(track);

        checkEquals(response, track);
    }

    @Test
    public void should_ReturnTrackEntity_IfMappedCorrectly() {
        TrackDTO dto = dtos.get(0);
        Track response = TrackMapper.getInstance().convertToEntity(dto);

        checkEquals(dto, response);
    }

    @Test
    public void should_ReturnTrackDTOs_IfMappedCorrectly() {
        List<TrackDTO> mappedDTOs = TrackMapper.getInstance().convertToDTO(tracks);

        Assertions.assertEquals(mappedDTOs.size(), tracks.size());

        for(int i = 0; i < mappedDTOs.size(); i++) {
            checkEquals(mappedDTOs.get(i), tracks.get(i));
        }
    }

    @Test
    public void should_ReturnTrackEntities_IfMappedCorrectly() {
        List<Track> mappedEntities = TrackMapper.getInstance().convertToEntity(dtos);

        Assertions.assertEquals(mappedEntities.size(), dtos.size());

        for(int i = 0; i < mappedEntities.size(); i++) {
            checkEquals(dtos.get(i), mappedEntities.get(i));
        }
    }

    private Track createTrack(int id, String title, String performer, int duration, String album,
                              int playcount, String publication, String description, boolean offline) {
        Track track = new Track();
        track.setId(id);
        track.setTitle(title);
        track.setPerformer(performer);
        track.setDuration(duration);
        track.setAlbum(album);
        track.setPlaycount(playcount);
        track.setPublicationDate(publication);
        track.setDescription(description);
        track.setOfflineAvailable(offline);

        return track;
    }

    private TrackDTO createTrackDTO(Track track) {
        TrackDTO dto = new TrackDTO();

        dto.setId(track.getId());
        dto.setTitle(track.getTitle());
        dto.setPerformer(track.getPerformer());
        dto.setDuration(track.getDuration());
        dto.setAlbum(track.getAlbum());
        dto.setPlaycount(track.getPlaycount());
        dto.setPublicationDate(track.getPublicationDate());
        dto.setOfflineAvailable(track.isOfflineAvailable());
        dto.setDescription(track.getDescription());

        return dto;
    }

    private void checkEquals(TrackDTO dto, Track track) {
        Assertions.assertEquals(dto.getId(), track.getId());
        Assertions.assertEquals(dto.getTitle(), track.getTitle());
        Assertions.assertEquals(dto.getPerformer(), track.getPerformer());
        Assertions.assertEquals(dto.getDuration(), track.getDuration());
        Assertions.assertEquals(dto.getAlbum(), track.getAlbum());
        Assertions.assertEquals(dto.getPlaycount(), track.getPlaycount());
        Assertions.assertEquals(dto.getPublicationDate(), track.getPublicationDate());
        Assertions.assertEquals(dto.getDescription(), track.getDescription());
        Assertions.assertEquals(dto.isOfflineAvailable(), track.isOfflineAvailable());
    }
}
