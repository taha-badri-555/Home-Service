package ir.maktabsharif.final_project_taha_badri.repository.user.expert;

import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.repository.base.user.BaseUserRepository;
import ir.maktabsharif.final_project_taha_badri.repository.base.user.BaseUserRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class ExpertRepositoryImpl
        extends BaseUserRepositoryImpl<Expert>
        implements ExpertRepository{

    protected ExpertRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    @Primary
    protected Class<Expert> getDomainClass() {
        return Expert.class;
    }

    @Override
    public void acceptExpertStatus(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Expert> update = cb.createCriteriaUpdate(Expert.class);
        Root<Expert> root = update.from(Expert.class);
        update.set("status", ExpertStatus.ACCEPT);
        update.where(cb.equal(root.get("id"), Expert.ID));
        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public void newExpertStatus(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Expert> update = cb.createCriteriaUpdate(Expert.class);
        Root<Expert> from = update.from(Expert.class);
        update.set("status", ExpertStatus.NEW);
        update.where(cb.equal(from.get("id"), Expert.ID));
        entityManager.createQuery(update).executeUpdate();
    }

    @Override
    public void waitingExpertStatus(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Expert> update = cb.createCriteriaUpdate(Expert.class);
        Root<Expert> from = update.from(Expert.class);
        update.set("status", ExpertStatus.WAITING);
        update.where(cb.equal(from.get("id"), Expert.ID));
        entityManager.createQuery(update).executeUpdate();

    }

    @Override
    public List<Expert> findAllWaitingExpertStatus() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Expert> query = cb.createQuery(Expert.class);
        Root<Expert> root = query.from(Expert.class);
        query.select(root).where(cb.equal(root.get("status"), ExpertStatus.WAITING));
        return entityManager.createQuery(query).getResultList();
    }

}
