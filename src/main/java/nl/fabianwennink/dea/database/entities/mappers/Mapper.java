package nl.fabianwennink.dea.database.entities.mappers;

import java.util.List;

public interface Mapper<T1, T2> {

    T1 convertToEntity(T2 dto);
    T2 convertToDTO(T1 entity);
    List<T1> convertToEntity(List<T2> dtos);
    List<T2> convertToDTO(List<T1> entities);
}
