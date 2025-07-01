package ir.maktabsharif.final_project_taha_badri.service.user.customer;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateCustomer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService extends BaseService<Customer,Long, SaveOrUpdateCustomer> {
    Customer saveCustomer(SaveOrUpdateCustomer dto);
}
