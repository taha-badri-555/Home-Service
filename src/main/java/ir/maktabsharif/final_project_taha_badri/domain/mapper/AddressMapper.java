package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.AddressRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.AddressResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper extends BaseMapper<AddressRequest, AddressResponse, Address, Long> {


    @Override
    Address requestToEntity(AddressRequest dto);

    //    @Mapping(target = "customerId", ignore = true)
    @Mapping(source = "customer.id", target = "customerId")
    @Override
    AddressRequest entityToRequest(Address entity);

    //    @Mapping(target = "customer", ignore = true)
    @Override
    void updateEntityWithRequest(AddressRequest dto, @MappingTarget Address entity);

    @Override
    Address responseToEntity(AddressResponse dto);

    @Mapping(source = "customer.id", target = "customerId")
    @Override
    AddressResponse entityToResponse(Address entity);
}
