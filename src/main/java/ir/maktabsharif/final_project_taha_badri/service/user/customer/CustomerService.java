package ir.maktabsharif.final_project_taha_badri.service.user.customer;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.AddressRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.CustomerRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.CustomerResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;

public interface CustomerService extends BaseService<Customer, Long, CustomerRequest, CustomerResponse> {

    Customer save(Customer customer);

    CustomerResponse register(CustomerRequest dto);

    CustomerResponse update(Long customerID, CustomerRequest dto);

    void addAddressToCustomer(AddressRequest dto);

}
