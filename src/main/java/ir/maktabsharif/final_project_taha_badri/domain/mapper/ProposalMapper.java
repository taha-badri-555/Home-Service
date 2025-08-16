package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ProposalRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.ProposalResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProposalMapper extends BaseMapper<ProposalRequest, ProposalResponse, Proposal, Long> {


//    @Mapping(target = "expert", ignore = true)

    /// /    @Mapping(target = "order", ignore = true)
    @Override
    Proposal requestToEntity(ProposalRequest dto);


    //    @Mapping(target = "expertId", ignore = true)
//    @Mapping(target = "orderId", ignore = true)

    @Override
    @Mapping(source = "order.id", target = "orderId")
    ProposalRequest entityToRequest(Proposal entity);


    //    @Mapping(target = "expert", ignore = true)
//    @Mapping(target = "order", ignore = true)
    @Override
    void updateEntityWithRequest(ProposalRequest dto, @MappingTarget Proposal entity);

    @Override
    Proposal responseToEntity(ProposalResponse dto);

    @Override
    @Mapping(source = "order.id", target = "orderId")
    ProposalResponse entityToResponse(Proposal entity);
}
