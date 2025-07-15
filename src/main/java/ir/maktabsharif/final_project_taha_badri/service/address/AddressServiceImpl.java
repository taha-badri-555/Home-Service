package ir.maktabsharif.final_project_taha_badri.service.address;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateAddress;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.AddressMapper;
import ir.maktabsharif.final_project_taha_badri.repository.address.AddressRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl
        extends BaseServiceImpl<
        Address,
        Long,
        AddressRepository,
        SaveOrUpdateAddress,
        AddressMapper>
        implements AddressService {
    private final CustomerService customerService;

    public AddressServiceImpl(
            AddressRepository repository,
            AddressMapper mapper,
            @Lazy CustomerService customerService) {
        super(repository, mapper);
        this.customerService = customerService;
    }

    @Override
    protected void setEntityRelations(Address entity, SaveOrUpdateAddress dto) {
        if (dto.customerId() != null) {
            Customer byId = customerService.findById(dto.customerId());
            entity.setCustomer(byId);
        }

    }

    @Override
    public void addAddressToCustomerAddressesByCustomerIdAndAddressId(Long customerId, Long addressId) {
        Customer customer = customerService.findById(customerId);
        repository.addAddressToCustomerAddressesByCustomerIdAndAddress(customer, addressId);
    }

    @Override
    public List<Address> findAll(int page, int size) {
        Page<Address> all = repository.findAll(PageRequest.of(page, page));
        return all.getContent();
    }
}
