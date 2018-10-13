package nl.fabianwennink.dea.database.entities.mappers;

import java.util.List;

public interface Mapper<T1, T2> {

    T1 convertToEntity(T2 dto, Object... args);
    T2 convertToDTO(T1 entity, Object... args);
    List<T1> convertToEntity(List<T2> dtos, Object... args);
    List<T2> convertToDTO(List<T1> entities, Object... args);
}
