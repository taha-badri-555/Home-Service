package ir.maktabsharif.final_project_taha_badri.service.address;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.AddressRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.AddressResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;

public interface AddressService extends BaseService<Address,Long, AddressRequest, AddressResponse > {
    void addAddressToCustomer(Long customerId, Long addressId);


}
