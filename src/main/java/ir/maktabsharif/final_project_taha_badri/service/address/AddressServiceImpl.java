package ir.maktabsharif.final_project_taha_badri.service.address;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.AddressRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.AddressResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.AddressMapper;
import ir.maktabsharif.final_project_taha_badri.repository.address.AddressRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AddressServiceImpl
        extends BaseServiceImpl<
        Address,
        Long,
        AddressRepository,
        AddressRequest,
        AddressResponse,
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
    protected void setEntityRelations(Address entity, AddressRequest dto) {
        if (dto.customerId() != null) {
            Customer byId = customerService.findById(dto.customerId());
            entity.setCustomer(byId);
        }

    }

    @Override
    public void addAddressToCustomer(Long customerId, Long addressId) {
        Customer customer = customerService.findById(customerId);
        repository.addAddressToCustomer(customer, addressId);
    }

}
