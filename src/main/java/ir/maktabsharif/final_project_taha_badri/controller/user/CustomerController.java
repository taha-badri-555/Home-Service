package ir.maktabsharif.final_project_taha_badri.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateAddress;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateCustomer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customers")
@RequiredArgsConstructor
@Tag(name = "CustomerController", description = "Controller for Customer.")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    @Operation(summary = "register customer", description = "register a new customer and initialize wallet & address")
    public ResponseEntity<Customer> registerCustomer(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateCustomer dto) {
        return ResponseEntity.ok(customerService.register(dto));
    }

    @PostMapping("/save")
    @Operation(summary = "save customer", description = "create a new customer (admin use)")
    public ResponseEntity<Customer> saveCustomer(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateCustomer dto) {
        return ResponseEntity.ok(customerService.save(dto));
    }

    @PutMapping("/update")
    @Operation(summary = "update customer", description = "update an existing customer")
    public ResponseEntity<Customer> updateCustomer(
            @RequestBody @Validated(ValidationGroup.Update.class) SaveOrUpdateCustomer dto) {
        return ResponseEntity.ok(customerService.update(dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete customer", description = "delete a customer by its id")
    public ResponseEntity<String> deleteCustomer(@RequestParam Long id) {
        customerService.deleteById(id);
        return ResponseEntity.ok("Deleted customer with id " + id);
    }

    @GetMapping("/find-by-id")
    @Operation(summary = "find customer by id", description = "retrieve a customer by its id")
    public ResponseEntity<Customer> findById(@RequestParam Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "find all customers", description = "list all customers with pagination")
    public ResponseEntity<List<Customer>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(customerService.findAll(page, size));
    }

    @PostMapping("/add-address")
    @Operation(summary = "add address to customer", description = "associate a new or existing address with a customer")
    public ResponseEntity<String> addAddressToCustomer(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateAddress dto) {
        customerService.addAddressToCustomer(dto);
        return ResponseEntity.ok("Added address " + dto + " to customer " + dto.customerId());
    }
}
