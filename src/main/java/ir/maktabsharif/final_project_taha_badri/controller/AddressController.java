package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateAddress;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/addresses")
@RequiredArgsConstructor
@Tag(name = "AddressController", description = "Controller for Address.")
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/save")
    @Operation(summary = "save address.", description = "save method for address.")
    public ResponseEntity<Address> addAddress(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateAddress dto) {
        return ResponseEntity.ok(addressService.save(dto));
    }

    @PutMapping("/update")
    @Operation(summary = "update address.", description = "update method for address.")
    public ResponseEntity<Address> updateAddress(
            @RequestBody @Validated(ValidationGroup.Update.class) SaveOrUpdateAddress dto) {
        return ResponseEntity.ok(addressService.update(dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete address.", description = "delete method for address.")
    public ResponseEntity<String> deleteAddress(@RequestParam Long id) {
        addressService.deleteById(id);
        return ResponseEntity.ok().body("Deleted address with id " + id);
    }

    @GetMapping("/find-by-id")
    @Operation(summary = "find address by id.", description = "find method for address based on id.")
    public ResponseEntity<Address> findById(@RequestParam Long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(
            summary = "find all addresses by page number and page size.",
            description = "(find All) method for addresses based on page number and page size.")
    public ResponseEntity<List<Address>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(addressService.findAll(page, size));
    }

    @PostMapping("/add-address-to-customer")
    @Operation(
            summary = "add address to customer.",
            description = "associate an existing address with a customer by their IDs.")
    public ResponseEntity<String> addAddressToCustomer(
            @RequestParam Long customerId,
            @RequestParam Long addressId) {
        addressService.addAddressToCustomerAddressesByCustomerIdAndAddressId(customerId, addressId);
        return ResponseEntity.ok()
                .body("Added address " + addressId + " to customer " + customerId);
    }
}
