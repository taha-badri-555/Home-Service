package ir.maktabsharif.final_project_taha_badri.repository.proposal;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class ProposalRepositoryImpl
        extends CrudRepositoryImpl<Proposal, Long>
        implements ProposalRepository {
    public ProposalRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Proposal> getDomainClass() {
        return Proposal.class;
    }
}
