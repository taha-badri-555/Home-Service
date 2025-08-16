package ir.maktabsharif.final_project_taha_badri.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ChangeImagePatch;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.ExpertRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.ExpertResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.ExpertMapper;
import ir.maktabsharif.final_project_taha_badri.service.user.expert.ExpertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/v1/experts")
@RequiredArgsConstructor
@Tag(name = "ExpertController", description = "Controller for Expert.")
public class ExpertController {
    private final ExpertService expertService;
    private final ExpertMapper mapper;


    @PostMapping
    @Operation(summary = "save expert", description = "create a new expert (admin use)")
    public ResponseEntity<ExpertResponse> saveExpert(
            @ModelAttribute @Validated(ValidationGroup.Save.class) ExpertRequest dto) {
        return ResponseEntity.ok(expertService.save(dto));
    }


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('EXPERT')")
    @Operation(summary = "update expert", description = "update expert data if not busy")
    public ResponseEntity<ExpertResponse> updateExpert(
            @AuthenticationPrincipal Person userDetails,
            @ModelAttribute @Validated(ValidationGroup.Update.class) ExpertRequest dto) {
        return ResponseEntity.ok(expertService.update(userDetails.getId(), dto));
    }

    @PutMapping("/change-image")
    @Operation(summary = "update expert image path", description = "update expert data if not busy")
    public ResponseEntity<ExpertResponse> changeImagePath(
            @AuthenticationPrincipal Person userDetails,
            @RequestPart @Valid MultipartFile image) throws IOException {
        return ResponseEntity.ok(expertService.changeImage(userDetails.getId(), new ChangeImagePatch(image)));
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Operation(summary = "delete expert", description = "delete an expert by its id")
    public ResponseEntity<String> deleteExpert(@PathVariable Long id) {
        expertService.deleteById(id);
        return ResponseEntity.ok("Deleted expert with id " + id);
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Operation(summary = "find expert by id", description = "retrieve an expert by its id")
    public ResponseEntity<ExpertResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(expertService.getResponseById(id));
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Operation(summary = "find all experts", description = "list all experts with pagination")
    public ResponseEntity<Page<ExpertResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(expertService.findAll(pageable));
    }

    @GetMapping("/waiting")
    @Operation(summary = "find all waiting experts", description = "list experts with status WAITING")
    public ResponseEntity<Page<ExpertResponse>> findAllWaiting(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(expertService.findAllWaitingExpertStatus(page, size));
    }

    @PutMapping("{status}")
    @Operation(summary = "change expert status", description = "update an expert's status")
    public ResponseEntity<String> changeStatus(
            @RequestParam Long expertId,
            @PathVariable ExpertStatus status) {
        expertService.changeExpertStatus(expertId, status);
        return ResponseEntity.ok("Expert " + expertId + " status changed to " + status);
    }

    @PutMapping("/accept/{expertId}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Operation(summary = "change expert status to Accept", description = "update an expert's status to Accept")
    public ResponseEntity<String> changeStatus(
            @PathVariable Long expertId) {
        expertService.changeExpertStatus(expertId, ExpertStatus.ACCEPT);
        return ResponseEntity.ok("Expert " + expertId + " status changed to " + ExpertStatus.ACCEPT);
    }


    @PutMapping("/add-service")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Operation(summary = "add service to expert", description = "associate a service with an expert")
    public ResponseEntity<String> addService(
            @RequestParam Long expertId,
            @RequestParam Long serviceId) {
        expertService.addService(expertId, serviceId);
        return ResponseEntity.ok("Service " + serviceId + " added to expert " + expertId);
    }


    @PutMapping("/remove-service")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Operation(summary = "remove service from expert", description = "dissociate a service from an expert")
    public ResponseEntity<String> removeService(
            @RequestParam Long expertId,
            @RequestParam Long serviceId) {
        expertService.removeService(expertId, serviceId);
        return ResponseEntity.ok("Service " + serviceId + " removed from expert " + expertId);
    }
}

