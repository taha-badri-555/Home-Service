package ir.maktabsharif.final_project_taha_badri.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.*;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseUser;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "Controller for BaseUser operations.")
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    @Operation(summary = "save user", description = "create a new user.")
    public ResponseEntity<BaseUser> saveUser(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateUser dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @PutMapping("/update")
    @Operation(summary = "update user", description = "update an existing user.")
    public ResponseEntity<BaseUser> updateUser(
            @RequestBody @Validated(ValidationGroup.Update.class) SaveOrUpdateUser dto) {
        return ResponseEntity.ok(userService.update(dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete user", description = "delete a user by its id.")
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("Deleted user with id " + id);
    }

    @GetMapping("/find-by-id")
    @Operation(summary = "find user by id", description = "retrieve a user by its id.")
    public ResponseEntity<BaseUser> findById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "find all users", description = "list all users with pagination.")
    public ResponseEntity<List<BaseUser>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(userService.findAll(page, size));
    }

    @PostMapping("/login")
    @Operation(summary = "login user", description = "authenticate a user by email and password.")
    public ResponseEntity<BaseUser> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.findByEmailAndPassword(request));
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
        return ResponseEntity.ok(userService.existsByEmailAndIdNot(new EmailAndIdRequest(id,email)));
    }

    @GetMapping("/exists-by-email-and-id")
    @Operation(summary = "check email and id", description = "check if an email and id pair exists.")
    public ResponseEntity<Boolean> existsByEmailAndId(
            @RequestParam String email,
            @RequestParam Long id) {
        return ResponseEntity.ok(userService.existsByEmailAndId(new EmailAndIdRequest(id,email)));
    }

    @PostMapping("/search")
    @Operation(summary = "search users", description = "search users by various criteria.")
    public ResponseEntity<List<BaseUser>> searchUsers(
            @RequestBody SearchRequest request) {
        return ResponseEntity.ok(userService.searchUsers(request));
    }
}
