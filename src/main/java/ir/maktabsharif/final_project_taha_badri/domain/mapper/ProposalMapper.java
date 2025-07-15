package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateProposal;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProposalMapper extends BaseMapper<SaveOrUpdateProposal, Proposal,Long> {
    @Override
    void updateEntityWithDTO(SaveOrUpdateProposal saveOrUpdateProposal, @MappingTarget Proposal entity);

    @Override
    Proposal dtoToEntity(SaveOrUpdateProposal saveOrUpdateProposal);

    @Override
    SaveOrUpdateProposal entityToDto(Proposal entity);
}
