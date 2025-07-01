package ir.maktabsharif.final_project_taha_badri.service.user.customer;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateAddress;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateCustomer;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.AddressMapper;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.CustomerMapper;
import ir.maktabsharif.final_project_taha_badri.repository.user.customer.CustomerRepository;
import ir.maktabsharif.final_project_taha_badri.service.address.AddressService;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private AddressService addressService;
    private AddressMapper addressMapper;
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerMapper = mock(CustomerMapper.class);
        addressService = mock(AddressService.class);
        addressMapper = mock(AddressMapper.class);
        customerService = new CustomerServiceImpl(
                customerRepository,
                customerMapper,
                addressService,
                addressMapper
        );
    }

    @Test
    void saveCustomer_shouldSaveCustomerWithAddress() {
        SaveOrUpdateAddress addressDto = mock(SaveOrUpdateAddress.class);
        SaveOrUpdateCustomer customerDto = mock(SaveOrUpdateCustomer.class);
        Address address = mock(Address.class);
        Customer customer = mock(Customer.class);

        when(customerDto.address()).thenReturn(addressDto);
        when(addressMapper.dtoToEntity(addressDto)).thenReturn(address);
        when(customerMapper.dtoToEntity(customerDto)).thenReturn(customer);
        when(customer.getAddresses()).thenReturn(new ArrayList<>());

        Customer result = customerService.saveCustomer(customerDto);

        verify(addressMapper).dtoToEntity(addressDto);
        verify(addressService).save(addressDto);
        verify(customerMapper).dtoToEntity(customerDto);
        verify(customer).getAddresses();
        verify(customerRepository).beginTransaction();
        verify(customerRepository).save(customer);
        verify(customerRepository).commitTransaction();

        assertEquals(customer, result);
    }
}
