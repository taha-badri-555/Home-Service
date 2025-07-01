package ir.maktabsharif.final_project_taha_badri.repository.user.customer;

import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.repository.base.user.BaseUserRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class CustomerRepositoryImpl
        extends BaseUserRepositoryImpl<Customer>
        implements CustomerRepository {

    protected CustomerRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Customer> getDomainClass() {
        return Customer.class;
    }
}
