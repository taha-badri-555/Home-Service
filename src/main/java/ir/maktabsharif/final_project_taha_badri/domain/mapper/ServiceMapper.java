package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateService;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ServiceMapper extends BaseMapper<SaveOrUpdateService, Service,Long> {
    @Override
    SaveOrUpdateService entityToDto(Service entity);

    @Override
    Service dtoToEntity(SaveOrUpdateService saveOrUpdateService);

    @Override
    void updateEntityWithDTO(SaveOrUpdateService saveOrUpdateService, @MappingTarget Service entity);
}
