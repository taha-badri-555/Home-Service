package ir.maktabsharif.final_project_taha_badri.service.user.customer;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateAddress;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateCustomer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

public interface CustomerService extends BaseService<Customer,Long, SaveOrUpdateCustomer> {
    Customer register(SaveOrUpdateCustomer dto);

    void addAddressToCustomer( SaveOrUpdateAddress dto);

}
