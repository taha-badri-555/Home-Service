package ir.maktabsharif.final_project_taha_badri.domain.mapper.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateExpert;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExpertMapper extends BaseMapper<SaveOrUpdateExpert, Expert,Long> {
    @Override
    SaveOrUpdateExpert entityToDto(Expert entity);

    @Override
    Expert dtoToEntity(SaveOrUpdateExpert saveOrUpdateExpert);

    @Override
    void updateEntityWithDTO(SaveOrUpdateExpert saveOrUpdateExpert, @MappingTarget Expert entity);
}
