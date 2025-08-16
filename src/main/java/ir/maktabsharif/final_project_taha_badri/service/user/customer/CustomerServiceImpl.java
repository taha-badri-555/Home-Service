package ir.maktabsharif.final_project_taha_badri.service.user.customer;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.AddressRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.EmailRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.WalletRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.CustomerRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.AddressResponse;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.CustomerResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.enums.Role;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.AddressMapper;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.CustomerMapper;
import ir.maktabsharif.final_project_taha_badri.exception.UserWithSameEmailExistsException;
import ir.maktabsharif.final_project_taha_badri.repository.user.customer.CustomerRepository;
import ir.maktabsharif.final_project_taha_badri.service.address.AddressService;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.email.EmailService;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import ir.maktabsharif.final_project_taha_badri.service.wallet.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerServiceImpl
        extends BaseServiceImpl<Customer,
        Long,
        CustomerRepository,
        CustomerRequest,
        CustomerResponse,
        CustomerMapper>
        implements CustomerService {
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private final UserService userService;
    private final WalletService walletService;
    private final EmailService emailService;

    public CustomerServiceImpl(
            CustomerRepository repository,
            CustomerMapper mapper, PasswordEncoder passwordEncoder,
            AddressService addressService,
            AddressMapper addressMapper,
            UserService userService, WalletService walletService, EmailService emailService) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
        this.addressService = addressService;
        this.addressMapper = addressMapper;
        this.userService = userService;
        this.walletService = walletService;
        this.emailService = emailService;
    }

    @Override
    public Customer save(Customer customer) {
        repository.save(customer);
        return customer;
    }

    @Transactional
    @Override
    public CustomerResponse register(CustomerRequest dto) {

        if (userService.existsByEmail(new EmailRequest(dto.email()))) {
            throw new UserWithSameEmailExistsException();
        }
        Customer customer = mapper.requestToEntity(dto);
        customer.setEmail(dto.email());
        customer.setRole(Role.CUSTOMER);
        customer.setPassword(passwordEncoder.encode(dto.password()));
        Customer saved = repository.save(customer);
        walletService.save(new WalletRequest(null, 0D, saved.getId()));
        AddressRequest addressRequest =
                new AddressRequest(null, dto.province(), dto.city(), dto.detail(), saved.getId());
        AddressResponse addressResponse = addressService.save(addressRequest);
        emailService.createAndSendVerificationMail(saved);

        return createCustomerResponseWithCustomerAndAddressDto(saved, addressResponse);
    }

    private CustomerResponse createCustomerResponseWithCustomerAndAddressDto(Customer customer, AddressResponse address) {
        return new CustomerResponse(
                customer.getId(),
                customer.getCreateDate(), customer.getLastUpdateDate(),
                customer.getFirstName(), customer.getLastName(),
                customer.getEmail(),address.id());
    }


    @Override
    public void addAddressToCustomer(AddressRequest dto) {
        Address address = addressMapper.requestToEntity(dto);
        addressService.addAddressToCustomer(dto.customerId(), address.getId());
    }

    @Override
    public CustomerResponse update(Long customerId, CustomerRequest customerDTO) {
        if (customerDTO.password() != null) {
            Customer byId = findById(customerId);
            mapper.updateEntityWithRequest(customerDTO, byId);
            byId.setPassword(passwordEncoder.encode(customerDTO.password()));
            repository.save(byId);
        }
        return super.update(customerDTO);
    }
}
