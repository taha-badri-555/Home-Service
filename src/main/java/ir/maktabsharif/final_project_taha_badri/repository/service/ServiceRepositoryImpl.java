package ir.maktabsharif.final_project_taha_badri.repository.service;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
@Repository
@Primary
public class ServiceRepositoryImpl
        extends CrudRepositoryImpl<Service, Long>
        implements ServiceRepository {

    public ServiceRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Service> getDomainClass() {
        return Service.class;
    }
}
