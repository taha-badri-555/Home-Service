package ir.maktabsharif.final_project_taha_badri.domain.mapper.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.ManagerRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.ManagerResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Manager;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ManagerMapper extends BaseMapper<ManagerRequest, ManagerResponse, Manager, Long> {

    @Override
    Manager requestToEntity(ManagerRequest dto);

    @Override
    ManagerRequest entityToRequest(Manager entity);

    @Override
    void updateEntityWithRequest(ManagerRequest dto, @MappingTarget Manager entity);

    @Override
    Manager responseToEntity(ManagerResponse dto);

    @Override
    ManagerResponse entityToResponse(Manager entity);
}
