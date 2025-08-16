package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.TransactionRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.TransactionResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Transaction;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper extends BaseMapper<TransactionRequest, TransactionResponse, Transaction, Long> {


    //    @Mapping(target = "customer", ignore = true)
//    @Mapping(target = "expert", ignore = true)
    @Override
    Transaction requestToEntity(TransactionRequest dto);


    //    @Mapping(target = "customerId", ignore = true)
//    @Mapping(target = "expertId", ignore = true)

    @Override
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "expert.id", target = "expertId")
    TransactionRequest entityToRequest(Transaction entity);


    //    @Mapping(target = "customer", ignore = true)
//    @Mapping(target = "expert", ignore = true)
    @Override
    void updateEntityWithRequest(TransactionRequest dto, @MappingTarget Transaction entity);

    @Override
    Transaction responseToEntity(TransactionResponse dto);

    @Override
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "expert.id", target = "expertId")
    TransactionResponse entityToResponse(Transaction entity);
}
