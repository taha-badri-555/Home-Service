package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateService;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceMapper extends BaseMapper<SaveOrUpdateService, Service> {
}
