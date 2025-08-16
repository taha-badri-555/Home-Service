package ir.maktabsharif.final_project_taha_badri.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.EmailAndIdRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.EmailRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.SearchRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.UserRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.UserResponse;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "Controller for BaseUser operations.")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "save user", description = "create a new user.")
    public ResponseEntity<UserResponse> saveUser(
            @RequestBody @Validated(ValidationGroup.Save.class) UserRequest dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @PutMapping
    @Operation(summary = "update user", description = "update an existing user.")
    public ResponseEntity<UserResponse> updateUser(
            @RequestBody @Validated(ValidationGroup.Update.class) UserRequest dto) {
        return ResponseEntity.ok(userService.update(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete user", description = "delete a user by its id.")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("Deleted user with id " + id);
    }

    @GetMapping("{id}")
    @Operation(summary = "find user by id", description = "retrieve a user by its id.")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getResponseById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "find all users", description = "list all users with pagination.")
    public ResponseEntity<Page<UserResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findAll(pageable));
    }


    @GetMapping("/exists-by-email")
    @Operation(summary = "check email existence", description = "check if an email is already used.")
    public ResponseEntity<Boolean> existsByEmail(
            @RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(new EmailRequest(email)));
    }

    @GetMapping("/exists-by-email-and-id-not")
    @Operation(summary = "check email for others", description = "check if an email is used by users other than the given id.")
    public ResponseEntity<Boolean> existsByEmailAndIdNot(
            @RequestParam String email,
            @RequestParam Long id) {
        return ResponseEntity.ok(userService.existsByEmailAndIdNot(new EmailAndIdRequest(id, email)));
    }

    @GetMapping("/exists-by-email-and-id")
    @Operation(summary = "check email and id", description = "check if an email and id pair exists.")
    public ResponseEntity<Boolean> existsByEmailAndId(
            @RequestParam String email,
            @RequestParam Long id) {
        return ResponseEntity.ok(userService.existsByEmailAndId(new EmailAndIdRequest(id, email)));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Operation(summary = "search users", description = "search users by various criteria.")
    public ResponseEntity<Page<UserResponse>> searchUsers(
            @RequestBody @Valid SearchRequest request,
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.searchUsers(request, pageable));
    }
}
