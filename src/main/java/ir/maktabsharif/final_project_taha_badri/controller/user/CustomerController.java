package ir.maktabsharif.final_project_taha_badri.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.AddressRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.CustomerRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.CustomerResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.CustomerMapper;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/customers")
@RequiredArgsConstructor
@Tag(name = "CustomerController", description = "Controller for Customer.")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper mapper;

    @PostMapping
    @Operation(summary = "save customer", description = "create a new customer (admin use)")
    public ResponseEntity<CustomerResponse> saveCustomer(
            @RequestBody @Validated(ValidationGroup.Save.class) CustomerRequest dto) {
        return ResponseEntity.ok(customerService.save(dto));
    }


    @PutMapping
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "update customer", description = "update an existing customer")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @AuthenticationPrincipal Person Person,
            @RequestBody @Validated(ValidationGroup.Update.class) CustomerRequest dto) {
        return ResponseEntity.ok(customerService.update(Person.getId(), dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete customer", description = "delete a customer by its id")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return ResponseEntity.ok("Deleted customer with id " + id);
    }

    @GetMapping("/by-id")
    @Operation(summary = "find customer by id", description = "retrieve a customer by its id")
    public ResponseEntity<CustomerResponse> findById(@RequestParam Long id) {
        return ResponseEntity.ok(customerService.getResponseById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "find all customers", description = "list all customers with pagination")
    public ResponseEntity<Page<CustomerResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(customerService.findAll(pageable));
    }

    @PostMapping("/add-address")
    @Operation(summary = "add address to customer", description = "associate a new or existing address with a customer")
    public ResponseEntity<String> addAddressToCustomer(
            @RequestBody @Validated(ValidationGroup.Save.class) AddressRequest dto) {
        customerService.addAddressToCustomer(dto);
        return ResponseEntity.ok("Added address " + dto + " to customer " + dto.customerId());
    }
}
