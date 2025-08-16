package ir.maktabsharif.final_project_taha_badri.domain.mapper.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.CustomerRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.CustomerResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper extends BaseMapper<CustomerRequest, CustomerResponse, Customer, Long> {

    @Override
    CustomerRequest entityToRequest(Customer entity);

    @Override
    void updateEntityWithRequest(CustomerRequest dto,@MappingTarget Customer entity);

    @Override
    Customer responseToEntity(CustomerResponse dto);

    @Override
    CustomerResponse entityToResponse(Customer entity);
}
