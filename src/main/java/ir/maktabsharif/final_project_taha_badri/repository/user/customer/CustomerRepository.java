package ir.maktabsharif.final_project_taha_badri.repository.user.customer;

import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.repository.base.user.BaseUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
        extends BaseUserRepository<Customer> {

}
