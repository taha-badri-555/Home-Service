package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import org.mapstruct.MappingTarget;


public interface BaseMapper<DTO, T extends BaseEntity<ID>, ID> {
    DTO entityToDto(T entity);
    T dtoToEntity(DTO dto);

    void updateEntityWithDTO(DTO dto, @MappingTarget T entity);
}
