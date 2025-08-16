package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.OrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.OrderResponse;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.OrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderMapper extends BaseMapper< OrderRequest,OrderResponse, Order, Long> {


    //    @Mapping(target = "customer", ignore = true)
//    @Mapping(target = "service", ignore = true)
//    @Mapping(target = "address", ignore = true)
//    @Mapping(target = "expert", ignore = true)
    @Override
    Order requestToEntity(OrderRequest dto);


    //    @Mapping(target = "customerId", ignore = true)
//    @Mapping(target = "serviceId", ignore = true)
//    @Mapping(target = "addressId", ignore = true)
//    @Mapping(target = "expertId", ignore = true)

    @Override
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "expert.id", target = "expertId")
    OrderRequest entityToRequest(Order entity);


    //    @Mapping(target = "customer", ignore = true)
//    @Mapping(target = "service", ignore = true)
//    @Mapping(target = "address", ignore = true)
//    @Mapping(target = "expert", ignore = true)
    @Override
    void updateEntityWithRequest(OrderRequest dto, @MappingTarget Order entity);

    @Override
    Order responseToEntity(OrderResponse dto);

    @Override
    @Mapping(source = "service.name", target = "serviceName")
    @Mapping(source = "address.province", target = "province")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.detail", target = "detail")
    @Mapping(source = "customer.firstName", target = "customerName")
    @Mapping(source = "customer.lastName", target = "customerLastName")
    @Mapping(source = "customer.email", target = "customerEmail")
    OrderResponse entityToResponse(Order entity);
}
