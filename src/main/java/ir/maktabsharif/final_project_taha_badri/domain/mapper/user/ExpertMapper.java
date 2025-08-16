package ir.maktabsharif.final_project_taha_badri.domain.mapper.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.ExpertRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.ExpertResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpertMapper extends BaseMapper<ExpertRequest, ExpertResponse, Expert, Long> {

//    @Mapping(target = "proposals", ignore = true)
//    @Mapping(target = "orders", ignore = true)
//    @Mapping(target = "wallet", ignore = true)
//    @Mapping(target = "services", ignore = true)
    @Override
    Expert requestToEntity(ExpertRequest dto);

//    @Mapping(target = "expertId", ignore = true)
    @Override
    ExpertRequest entityToRequest(Expert entity);

//    @Mapping(target = "proposals", ignore = true)
//    @Mapping(target = "orders", ignore = true)
//    @Mapping(target = "wallet", ignore = true)
//    @Mapping(target = "services", ignore = true)
    @Override
    void updateEntityWithRequest(ExpertRequest dto, @MappingTarget Expert entity);

    @Override
    Expert responseToEntity(ExpertResponse dto);

    @Override
    ExpertResponse entityToResponse(Expert entity);
}
