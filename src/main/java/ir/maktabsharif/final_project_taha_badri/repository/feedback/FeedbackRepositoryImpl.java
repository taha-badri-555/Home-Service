package ir.maktabsharif.final_project_taha_badri.repository.feedback;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepository;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class FeedbackRepositoryImpl
        extends CrudRepositoryImpl<Feedback, Long>
        implements FeedbackRepository {
    public FeedbackRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Feedback> getDomainClass() {
        return Feedback.class;
    }
}
