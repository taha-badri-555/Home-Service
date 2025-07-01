package ir.maktabsharif.final_project_taha_badri.service.proposal;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateProposal;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service

public interface ProposalService extends BaseService<Proposal,Long, SaveOrUpdateProposal> {

}
