package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.FeedbackRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.PaymentRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.WalletRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.WalletResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.WalletMapper;
import ir.maktabsharif.final_project_taha_badri.service.recaptcha.RecaptchaService;
import ir.maktabsharif.final_project_taha_badri.service.wallet.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/wallets")
@RequiredArgsConstructor
@Tag(name = "WalletController", description = "Controller for Wallet operations.")
public class WalletController {
    private final RecaptchaService recaptchaService;
    private final WalletService walletService;
    private final WalletMapper mapper;

    @PostMapping
    @Operation(summary = "save wallet", description = "create a new wallet for a user.")
    public ResponseEntity<WalletResponse> saveWallet(
            @RequestBody @Validated(ValidationGroup.Save.class) WalletRequest dto) {
        return ResponseEntity.ok(walletService.save(dto));
    }

    @PutMapping
    @Operation(summary = "update wallet", description = "update an existing wallet.")
    public ResponseEntity<WalletResponse> updateWallet(
            @RequestBody @Validated(ValidationGroup.Update.class) WalletRequest dto) {
        return ResponseEntity.ok(walletService.update(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete wallet", description = "delete a wallet by its id.")
    public ResponseEntity<String> deleteWallet(@PathVariable Long id) {
        walletService.deleteById(id);
        return ResponseEntity.ok("Deleted wallet with id " + id);
    }

    @GetMapping("{id}")
    @Operation(summary = "find wallet by id", description = "retrieve a wallet by its id.")
    public ResponseEntity<WalletResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(walletService.getResponseById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "find all wallets", description = "list all wallets with pagination.")
    public ResponseEntity<Page<WalletResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(walletService.findAll(pageable));
    }

    @PostMapping("/add-credit")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "add credit to wallet", description = "add specified credit to user's wallet.")
    public ResponseEntity<Void> addCredit(
            @AuthenticationPrincipal Person userDetails,
            @RequestBody @Valid PaymentRequest dto) {
        if (dto.clientTimeLeft() <= 0) {
            return ResponseEntity.badRequest().build();

        }
        if (!recaptchaService.isValid(dto.recaptcha())) {
            return ResponseEntity.badRequest().build();
        }
        walletService.addCreditToWallet(dto.amount(), userDetails.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pay")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "pay from wallet", description = "process payment for an order and record transaction.")
    public ResponseEntity<WalletResponse> payFromWallet(
            @AuthenticationPrincipal Person userDetails,
            @RequestBody FeedbackRequest feedbackDto) {
        return ResponseEntity.ok(walletService.payFromWallet(userDetails.getId(), feedbackDto));
    }

    @GetMapping("/by-user")
    @Operation(summary = "find wallet by user", description = "retrieve wallet associated with a user id.")
    public ResponseEntity<WalletResponse> findByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(walletService.findByUser_Id(userId));
    }

    @GetMapping("/amount")
    @PreAuthorize("hasAnyRole('EXPERT','CUSTOMER')")
    @Operation(summary = "get amount.", description = "get amount by user ID.")
    public ResponseEntity<Double> getUserWalletAmount(@AuthenticationPrincipal Person userDetails) {
        Double amount = walletService.getAmount(userDetails.getId());
        if (amount == null) {
            amount = 0.0;
        }
        return ResponseEntity.ok(amount);
    }
}
