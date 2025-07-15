package ir.maktabsharif.final_project_taha_badri.service.user.customer;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateAddress;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateWallet;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.CustomerUpdate;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.EmailRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateCustomer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.AddressMapper;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.CustomerMapper;
import ir.maktabsharif.final_project_taha_badri.exception.UserWithSameEmailExistsException;
import ir.maktabsharif.final_project_taha_badri.repository.user.customer.CustomerRepository;
import ir.maktabsharif.final_project_taha_badri.service.address.AddressService;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import ir.maktabsharif.final_project_taha_badri.service.wallet.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerServiceImpl
        extends BaseServiceImpl<Customer,
        Long,
        CustomerRepository,
        SaveOrUpdateCustomer,
        CustomerMapper>
        implements CustomerService {
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private final UserService userService;
    private final WalletService walletService;

    public CustomerServiceImpl(
            CustomerRepository repository,
            CustomerMapper mapper,
            AddressService addressService,
            AddressMapper addressMapper,
            UserService userService, WalletService walletService) {
        super(repository, mapper);
        this.addressService = addressService;
        this.addressMapper = addressMapper;
        this.userService = userService;
        this.walletService = walletService;
    }

    @Override
    public Customer register(SaveOrUpdateCustomer dto) {

        if (userService.existsByEmail(new EmailRequest(dto.email()))) {
            throw new UserWithSameEmailExistsException();
        }
        Customer customer = mapper.dtoToEntity(dto);
        customer.setEmail(dto.email());
        Customer saved = repository.save(customer);
        walletService.save(new SaveOrUpdateWallet(null, 0D, saved.getId()));
        addressService.save(new SaveOrUpdateAddress(null, dto.province(), dto.city(), dto.detail(), saved.getId()));
        return customer;
    }


    public Customer update(CustomerUpdate dto) {
        Customer byId = findById(dto.getId());
        mapper.updateEntityWithDTO(dto,byId);
        return repository.save(byId);
    }

    @Override
    public void addAddressToCustomer(SaveOrUpdateAddress dto) {
        Address address = addressMapper.dtoToEntity(dto);
        addressService.addAddressToCustomerAddressesByCustomerIdAndAddressId(dto.customerId(), address.getId());
    }

}
