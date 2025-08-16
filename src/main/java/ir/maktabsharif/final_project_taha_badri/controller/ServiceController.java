package ir.maktabsharif.final_project_taha_badri.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ServiceRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.ServiceResponse;
import ir.maktabsharif.final_project_taha_badri.service.home_service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/services")
@RequiredArgsConstructor
@Tag(name = "ServiceController", description = "Controller for managing services.")
public class ServiceController {

    private final ServiceService serviceService;


    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Create service", description = "Create a new service")
    public ResponseEntity<ServiceResponse> saveService(
            @RequestBody @Validated ServiceRequest dto) {
        return ResponseEntity.ok(serviceService.save(dto));
    }

    @PutMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Update service", description = "Update an existing service")
    public ResponseEntity<ServiceResponse> updateService(
            @RequestBody @Validated ServiceRequest dto) {
        return ResponseEntity.ok(serviceService.update(dto));
    }

    @GetMapping("/parents")
    @PreAuthorize("hasAnyRole('MANAGER','CUSTOMER')")
    @Operation(
            summary = "Get parent services",
            description = "Get all top-level services (without parents) with pagination using page and size parameters"
    )
    public ResponseEntity<Page<ServiceResponse>> getAllParentServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ServiceResponse> services = serviceService.getAllParentServices(pageable);
        return ResponseEntity.ok(services);
    }


    @GetMapping("/children")
    @PreAuthorize("hasAnyRole('MANAGER','CUSTOMER')")
    @Operation(summary = "Get child services by parentId", description = "Get child services of a specific parent")
    public ResponseEntity<Page<ServiceResponse>> getAllChildServices(
            @RequestParam Long parentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ServiceResponse> services = serviceService.getAllChildServiceByParentId(parentId, pageable);
        return ResponseEntity.ok(services);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','CUSTOMER')")
    @Operation(summary = "Find service by id", description = "Retrieve a service by its id")
    public ResponseEntity<ServiceResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getResponseById(id));
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Operation(summary = "Delete service by id", description = "Delete a service by its id")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        serviceService.deleteById(id);
        return ResponseEntity.ok("Deleted service with id " + id);
    }
}

