package ir.maktabsharif.final_project_taha_badri.repository.base.user;


import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseUser;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public abstract class BaseUserRepositoryImpl<T extends BaseUser> extends CrudRepositoryImpl<T, Long>
        implements BaseUserRepository<T> {

    protected BaseUserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public T save(T user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public Optional<T> findByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getDomainClass());
        Root<T> from = query.from(getDomainClass());
        query.select(from);
        query.where(
        );
        return Optional.ofNullable(
                entityManager.createQuery(query).getSingleResult()
        );
    }

    @Override
    public Optional<T> findByUsernameAndPassword(String username, String password) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getDomainClass());
        Root<T> from = query.from(getDomainClass());
        query.select(from);
        query.where(
                cb.and(

                        cb.equal(from.get(BaseUser.PASSWORD_COLUMN), password)

                )
        );
        return Optional.ofNullable(
                entityManager.createQuery(query).getSingleResult()
        );
    }
}