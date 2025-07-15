package ir.maktabsharif.final_project_taha_badri.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateExpert;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.service.user.expert.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/experts")
@RequiredArgsConstructor
@Tag(name = "ExpertController", description = "Controller for Expert.")
public class ExpertController {
    private final ExpertService expertService;

    @PostMapping("/register")
    @Operation(summary = "register expert", description = "register a new expert (initial status NEW or WAITING)")
    public ResponseEntity<Expert> registerExpert(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateExpert dto) {
        return ResponseEntity.ok(expertService.register(dto));
    }

    @PostMapping("/save")
    @Operation(summary = "save expert", description = "create a new expert (admin use)")
    public ResponseEntity<Expert> saveExpert(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateExpert dto) {
        return ResponseEntity.ok(expertService.save(dto));
    }

    @PutMapping("/update")
    @Operation(summary = "update expert", description = "update expert data if not busy")
    public ResponseEntity<Expert> updateExpert(
            @RequestBody @Validated(ValidationGroup.Update.class) SaveOrUpdateExpert dto) {
        return ResponseEntity.ok(expertService.update(dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete expert", description = "delete an expert by its id")
    public ResponseEntity<String> deleteExpert(@RequestParam Long id) {
        expertService.deleteById(id);
        return ResponseEntity.ok("Deleted expert with id " + id);
    }

    @GetMapping("/find-by-id")
    @Operation(summary = "find expert by id", description = "retrieve an expert by its id")
    public ResponseEntity<Expert> findById(@RequestParam Long id) {
        return ResponseEntity.ok(expertService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "find all experts", description = "list all experts with pagination")
    public ResponseEntity<List<Expert>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(expertService.findAll(page, size));
    }

    @GetMapping("/waiting")
    @Operation(summary = "find all waiting experts", description = "list experts with status WAITING")
    public ResponseEntity<List<Expert>> findAllWaiting() {
        return ResponseEntity.ok(expertService.findAllWaitingExpertStatus());
    }

    @PutMapping("/change-status")
    @Operation(summary = "change expert status", description = "update an expert's status")
    public ResponseEntity<String> changeStatus(
            @RequestParam Long expertId,
            @RequestParam ExpertStatus status) {
        expertService.changeExpertStatus(expertId, status);
        return ResponseEntity.ok("Expert " + expertId + " status changed to " + status);
    }

    @PostMapping("/add-service")
    @Operation(summary = "add service to expert", description = "associate a service with an expert")
    public ResponseEntity<String> addService(
            @RequestParam Long expertId,
            @RequestParam Long serviceId) {
        expertService.addService(expertId, serviceId);
        return ResponseEntity.ok("Service " + serviceId + " added to expert " + expertId);
    }

    @PostMapping("/remove-service")
    @Operation(summary = "remove service from expert", description = "dissociate a service from an expert")
    public ResponseEntity<String> removeService(
            @RequestParam Long expertId,
            @RequestParam Long serviceId) {
        expertService.removeService(expertId, serviceId);
        return ResponseEntity.ok("Service " + serviceId + " removed from expert " + expertId);
    }
}

