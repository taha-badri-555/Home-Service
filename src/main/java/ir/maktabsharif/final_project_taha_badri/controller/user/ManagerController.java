package ir.maktabsharif.final_project_taha_badri.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.ManagerRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.ManagerResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Manager;
import ir.maktabsharif.final_project_taha_badri.service.user.managert.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/managers")
@RequiredArgsConstructor
@Tag(name = "ManagerController", description = "Controller for Manager.")
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    @Operation(summary = "save manager", description = "create a new manager.")
    public ResponseEntity<ManagerResponse> addManager(
            @RequestBody @Validated(ValidationGroup.Save.class) ManagerRequest dto) {
        return ResponseEntity.ok(managerService.save(dto));
    }

    @PutMapping
    @Operation(summary = "update manager", description = "update an existing manager.")
    public ResponseEntity<ManagerResponse> updateManager(
            @RequestBody @Validated(ValidationGroup.Update.class) ManagerRequest dto) {
        return ResponseEntity.ok(managerService.update(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete manager", description = "delete a manager by its id.")
    public ResponseEntity<String> deleteManager(@PathVariable Long id) {
        managerService.deleteById(id);
        return ResponseEntity.ok("Deleted manager with id " + id);
    }

    @GetMapping("{id}")
    @Operation(summary = "find manager by id", description = "retrieve a manager by its id.")
    public ResponseEntity<Manager> findById(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.findById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "find all managers", description = "list all managers with pagination.")
    public ResponseEntity<Page<ManagerResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(managerService.findAll(pageable));
    }
}
