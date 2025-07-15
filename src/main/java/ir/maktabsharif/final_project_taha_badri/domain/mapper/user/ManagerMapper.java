package ir.maktabsharif.final_project_taha_badri.domain.mapper.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateManager;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Manager;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper extends BaseMapper<SaveOrUpdateManager, Manager,Long> {
    @Override
    SaveOrUpdateManager entityToDto(Manager entity);

    @Override
    Manager dtoToEntity(SaveOrUpdateManager saveOrUpdateManager);

    @Override
    void updateEntityWithDTO(SaveOrUpdateManager saveOrUpdateManager, @MappingTarget Manager entity);
}
