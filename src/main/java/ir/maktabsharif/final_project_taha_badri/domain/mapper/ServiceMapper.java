package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ServiceRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.ServiceResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ServiceMapper extends BaseMapper<ServiceRequest, ServiceResponse, Service, Long> {


//    @Mapping(target = "parent", ignore = true)
    @Override
    Service requestToEntity(ServiceRequest dto);


//    @Mapping(target = "serviceId", ignore = true)

    @Override
    @Mapping(source = "parentService.id",target = "serviceId")
    ServiceRequest entityToRequest(Service entity);


//    @Mapping(target = "parent", ignore = true)
    @Override
    void updateEntityWithRequest(ServiceRequest dto, @MappingTarget Service entity);

    @Override
    Service responseToEntity(ServiceResponse dto);

    @Override
    @Mapping(source = "parentService.id",target = "serviceId")
    ServiceResponse entityToResponse(Service entity);
}
