package ir.maktabsharif.final_project_taha_badri.service.address;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateAddress;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;

import java.util.List;

public interface AddressService extends BaseService<Address,Long, SaveOrUpdateAddress> {
    void addAddressToCustomerAddressesByCustomerIdAndAddressId(Long customerId, Long addressId);

    List<Address> findAll(int page, int size);
}
