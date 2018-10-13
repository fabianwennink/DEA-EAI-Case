package nl.fabianwennink.dea.database.entities.mappers;

import nl.fabianwennink.dea.controllers.tracks.dto.TrackDTO;
import nl.fabianwennink.dea.database.entities.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackMapper implements Mapper<Track, TrackDTO> {

    private static TrackMapper mapper;

    @Override
    public Track convertToEntity(TrackDTO dto, Object... args) {
        Track track = new Track();
        track.setId(dto.getId());
        track.setTitle(dto.getTitle());
        track.setPerformer(dto.getPerformer());
        track.setDuration(dto.getDuration());
        track.setAlbum(dto.getAlbum());
        track.setPlaycount(dto.getPlaycount());
        track.setPublicationDate(dto.getPublicationDate());
        track.setDescription(dto.getDescription());
        track.setOfflineAvailable(dto.isOfflineAvailable());

        return track;
    }

    @Override
    public TrackDTO convertToDTO(Track entity, Object... args) {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(entity.getId());
        trackDTO.setTitle(entity.getTitle());
        trackDTO.setPerformer(entity.getPerformer());
        trackDTO.setDuration(entity.getDuration());
        trackDTO.setAlbum(entity.getAlbum());
        trackDTO.setPlaycount(entity.getPlaycount());
        trackDTO.setPublicationDate(entity.getPublicationDate());
        trackDTO.setDescription(entity.getDescription());
        trackDTO.setOfflineAvailable(entity.isOfflineAvailable());

        return trackDTO;
    }

    @Override
    public List<Track> convertToEntity(List<TrackDTO> dtos, Object... args) {
        List<Track> tracks = new ArrayList<>();

        for(TrackDTO dto : dtos) {
            tracks.add(convertToEntity(dto, args));
        }

        return tracks;
    }

    @Override
    public List<TrackDTO> convertToDTO(List<Track> entities, Object... args) {
        List<TrackDTO> tracks = new ArrayList<>();

        for(Track track : entities) {
            tracks.add(convertToDTO(track, args));
        }

        return tracks;
    }

    public static TrackMapper getInstance() {
        if(mapper == null) {
            mapper = new TrackMapper();
        }

        return mapper;
    }
}
