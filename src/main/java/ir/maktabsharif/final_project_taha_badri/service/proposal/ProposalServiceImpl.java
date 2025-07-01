package ir.maktabsharif.final_project_taha_badri.service.proposal;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateProposal;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.ProposalMapper;
import ir.maktabsharif.final_project_taha_badri.repository.proposal.ProposalRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ProposalServiceImpl
        extends BaseServiceImpl<
        Proposal,
        Long,
        ProposalRepository,
        ProposalMapper,
        SaveOrUpdateProposal>
        implements ProposalService {

    public ProposalServiceImpl(ProposalRepository repository, ProposalMapper mapper) {
        super(repository, mapper);
    }
}
