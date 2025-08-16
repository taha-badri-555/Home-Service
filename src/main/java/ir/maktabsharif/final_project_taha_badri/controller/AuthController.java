package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.AuthResponse;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.CustomerRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.ExpertRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.LoginRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.CustomerResponse;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.ExpertResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.service.email.EmailService;
import ir.maktabsharif.final_project_taha_badri.service.security.JwtUtil;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerService;
import ir.maktabsharif.final_project_taha_badri.service.user.expert.ExpertService;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final ExpertService expertService;
    private final CustomerService customerService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtil.generateToken((Person) userDetails);

        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }

    @GetMapping("/verify")
    @PermitAll
    public ResponseEntity<String> verifyToken(@RequestParam("token") String token) {
        String result = emailService.verifyEmail(token);
        if (result.equals("Token expired")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/expert/register")
    @Operation(summary = "register expert", description = "register a new expert (initial status NEW or WAITING)")
    public ResponseEntity<ExpertResponse> registerExpert(
            @Validated(ValidationGroup.Save.class) @RequestPart("firstName") String firstName,
            @Validated(ValidationGroup.Save.class) @RequestPart("lastName") String lastName,
            @Validated(ValidationGroup.Save.class) @RequestPart("password") String password,
            @Validated(ValidationGroup.Save.class) @RequestPart("email") String email,
            @Validated(ValidationGroup.Save.class) @RequestPart("image") MultipartFile image) throws IOException {

        return ResponseEntity.ok(expertService.register(
                new ExpertRequest(firstName, lastName, password, email, image, null, null, null)));
    }

    @PostMapping("/customer/register")
    @Operation(summary = "register customer", description = "register a new customer and initialize wallet & address")
    public ResponseEntity<CustomerResponse> registerCustomer(
            @RequestBody @Validated(ValidationGroup.Save.class) CustomerRequest dto) {
        return ResponseEntity.ok(customerService.register(dto));
    }


}
