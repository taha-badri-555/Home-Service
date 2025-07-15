package ir.maktabsharif.final_project_taha_badri.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateManager;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Manager;
import ir.maktabsharif.final_project_taha_badri.service.user.managert.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/managers")
@RequiredArgsConstructor
@Tag(name = "ManagerController", description = "Controller for Manager.")
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/save")
    @Operation(summary = "save manager", description = "create a new manager.")
    public ResponseEntity<Manager> addManager(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateManager dto) {
        return ResponseEntity.ok(managerService.save(dto));
    }

    @PutMapping("/update")
    @Operation(summary = "update manager", description = "update an existing manager.")
    public ResponseEntity<Manager> updateManager(
            @RequestBody @Validated(ValidationGroup.Update.class) SaveOrUpdateManager dto) {
        return ResponseEntity.ok(managerService.update(dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete manager", description = "delete a manager by its id.")
    public ResponseEntity<String> deleteManager(@RequestParam Long id) {
        managerService.deleteById(id);
        return ResponseEntity.ok("Deleted manager with id " + id);
    }

    @GetMapping("/find-by-id")
    @Operation(summary = "find manager by id", description = "retrieve a manager by its id.")
    public ResponseEntity<Manager> findById(@RequestParam Long id) {
        return ResponseEntity.ok(managerService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "find all managers", description = "list all managers with pagination.")
    public ResponseEntity<List<Manager>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(managerService.findAll(page, size));
    }
}
