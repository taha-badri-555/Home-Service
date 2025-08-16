package ir.maktabsharif.final_project_taha_badri.service.address;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.AddressRequest;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.repository.address.AddressRepository;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AddressServiceImplTest {

    @Mock
    private AddressRepository repository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void setEntityRelations_shouldSetCustomer_whenCustomerIdIsNotNull() {
        AddressRequest dto = mock(AddressRequest.class);
        Address entity = new Address();
        Customer customer = new Customer();

        when(dto.customerId()).thenReturn(1L);
        when(customerService.findById(1L)).thenReturn(customer);

        addressService.setEntityRelations(entity, dto);

        verify(customerService, times(1)).findById(1L);
        assertEquals(customer, entity.getCustomer());
    }

    @Test
    void setEntityRelations_shouldNotSetCustomer_whenCustomerIdIsNull() {
        AddressRequest dto = mock(AddressRequest.class);
        Address entity = new Address();

        when(dto.customerId()).thenReturn(null);

        addressService.setEntityRelations(entity, dto);

        verify(customerService, never()).findById(any());
        assertNull(entity.getCustomer());
    }

    @Test
    void addAddressToCustomer_shouldCallRepositoryAddAddressToCustomer() {
        Long customerId = 1L;
        Long addressId = 2L;
        Customer customer = new Customer();

        when(customerService.findById(customerId)).thenReturn(customer);

        addressService.addAddressToCustomer(customerId, addressId);
        verify(customerService, times(1)).findById(customerId);
        verify(repository, times(1)).addAddressToCustomer(customer, addressId);
    }
}
