package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.AddressRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.AddressResponse;
import ir.maktabsharif.final_project_taha_badri.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "v1/addresses")
@RequiredArgsConstructor
@Tag(name = "AddressController", description = "Controller for Address.")
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    @Operation(summary = "save address.", description = "save method for address.")
    public ResponseEntity<AddressResponse> addAddress(
            @RequestBody @Validated(ValidationGroup.Save.class) AddressRequest dto) {
        return ResponseEntity.ok(addressService.save(dto));
    }

    @PutMapping
    @Operation(summary = "update address.", description = "update method for address.")
    public ResponseEntity<AddressResponse> updateAddress(
            @RequestBody @Validated(ValidationGroup.Update.class) AddressRequest dto) {
        return ResponseEntity.ok(addressService.update(dto));
    }

    @DeleteMapping
    @Operation(summary = "delete address.", description = "delete method for address.")
    public ResponseEntity<String> deleteAddress(@RequestParam Long id) {
        addressService.deleteById(id);
        return ResponseEntity.ok().body("Deleted address with id " + id);
    }

    @GetMapping("{id}")
    @Operation(summary = "find address by id.", description = "find method for address based on id.")
    public ResponseEntity<AddressResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getResponseById(id));
    }

    @GetMapping
    @Operation(
            summary = "find all addresses by page number and page size.",
            description = "(find All) method for addresses based on page number and page size.")
    public ResponseEntity<Page<AddressResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(addressService.findAll(pageable));
    }

    @PostMapping("{addressId}/customer/{customerId}")
    @Operation(
            summary = "add address to customer.",
            description = "associate an existing address with a customer by their IDs.")
    public ResponseEntity<String> addAddressToCustomer(
            @PathVariable Long customerId,
            @PathVariable Long addressId) {
        addressService.addAddressToCustomer(customerId, addressId);
        return ResponseEntity.ok()
                .body("Added address " + addressId + " to customer " + customerId);
    }
}
