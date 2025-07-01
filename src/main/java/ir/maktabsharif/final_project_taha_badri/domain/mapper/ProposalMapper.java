package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateProposal;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProposalMapper extends BaseMapper<SaveOrUpdateProposal, Proposal> {
}
