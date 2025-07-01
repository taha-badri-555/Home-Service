package ir.maktabsharif.final_project_taha_badri.service.user.customer;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateCustomer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.AddressMapper;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.CustomerMapper;
import ir.maktabsharif.final_project_taha_badri.repository.user.customer.CustomerRepository;
import ir.maktabsharif.final_project_taha_badri.service.address.AddressService;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary

public class CustomerServiceImpl
        extends BaseServiceImpl<Customer,
        Long,
        CustomerRepository,
        CustomerMapper,
        SaveOrUpdateCustomer>
        implements CustomerService {
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private CustomerMapper customerMapper;

    public CustomerServiceImpl(
            CustomerRepository repository,
            CustomerMapper mapper,
            AddressService addressService,
            AddressMapper addressMapper) {
        super(repository, mapper);
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @Override
    public Customer saveCustomer(SaveOrUpdateCustomer dto) {
        Address address = addressMapper.dtoToEntity(dto.address());
        addressService.save(dto.address());
        Customer customer = mapper.dtoToEntity(dto);
        customer.getAddresses().add(address);
        repository.beginTransaction();
        repository.save(customer);
        repository.commitTransaction();
        return customer;
    }
}
