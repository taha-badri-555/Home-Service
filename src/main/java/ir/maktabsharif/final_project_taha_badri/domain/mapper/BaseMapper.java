package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BaseMapper<DTO, T> {
    DTO entityToDto(T entity);
    T dtoToEntity(DTO dto);
}
