package ir.maktabsharif.final_project_taha_badri.repository.user.manager;

import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Manager;
import ir.maktabsharif.final_project_taha_badri.repository.base.user.BaseUserRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class ManagerRepositoryImpl
        extends BaseUserRepositoryImpl<Manager>
        implements ManagerRepository {

    protected ManagerRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Manager> getDomainClass() {
        return Manager.class;
    }
}
