package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.PaymentRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateFeedback;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateWallet;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import ir.maktabsharif.final_project_taha_badri.service.wallet.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/wallets")
@RequiredArgsConstructor
@Tag(name = "WalletController", description = "Controller for Wallet operations.")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/save")
    @Operation(summary = "save wallet", description = "create a new wallet for a user.")
    public ResponseEntity<Wallet> saveWallet(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateWallet dto) {
        return ResponseEntity.ok(walletService.save(dto));
    }

    @PutMapping("/update")
    @Operation(summary = "update wallet", description = "update an existing wallet.")
    public ResponseEntity<Wallet> updateWallet(
            @RequestBody @Validated(ValidationGroup.Update.class) SaveOrUpdateWallet dto) {
        return ResponseEntity.ok(walletService.update(dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete wallet", description = "delete a wallet by its id.")
    public ResponseEntity<String> deleteWallet(@RequestParam Long id) {
        walletService.deleteById(id);
        return ResponseEntity.ok("Deleted wallet with id " + id);
    }

    @GetMapping("/find-by-id")
    @Operation(summary = "find wallet by id", description = "retrieve a wallet by its id.")
    public ResponseEntity<Wallet> findById(@RequestParam Long id) {
        return ResponseEntity.ok(walletService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "find all wallets", description = "list all wallets with pagination.")
    public ResponseEntity<List<Wallet>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(walletService.findAll(page, size));
    }

    @PostMapping("/add-credit")
    @Operation(summary = "add credit to wallet", description = "add specified credit to user's wallet.")
    public ResponseEntity<Void> addCredit(

            @RequestParam Long userId,
            @RequestBody @Valid PaymentRequest  dto) {
        if (dto.clientTimeLeft()<=0){
            return  ResponseEntity.badRequest().build();

        }
        walletService.addCreditToWallet(dto.amount(), userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pay")
    @Operation(summary = "pay from wallet", description = "process payment for an order and record transaction.")
    public ResponseEntity<Wallet> payFromWallet(
            @RequestParam Long orderId,
            @RequestBody SaveOrUpdateFeedback feedbackDto) {
        return ResponseEntity.ok(walletService.payFromWallet(orderId, feedbackDto));
    }

    @GetMapping("/by-user")
    @Operation(summary = "find wallet by user", description = "retrieve wallet associated with a user id.")
    public ResponseEntity<Wallet> findByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(walletService.findByUser_Id(userId));
    }
}
