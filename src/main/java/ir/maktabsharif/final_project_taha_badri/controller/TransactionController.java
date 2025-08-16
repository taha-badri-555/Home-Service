package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.TransactionRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.TransactionResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.service.transaction.TransactionService;
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
@RequestMapping(path = "/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "TransactionController", description = "Controller for Transaction.")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "save transaction", description = "create a new transaction (expert-customer payment).")
    public ResponseEntity<TransactionResponse> addTransaction(
            @RequestBody @Validated(ValidationGroup.Save.class) TransactionRequest dto) {
        return ResponseEntity.ok(transactionService.save(dto));
    }

    @PutMapping
    @Operation(summary = "update transaction", description = "update an existing transaction.")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @RequestBody @Validated(ValidationGroup.Update.class) TransactionRequest dto) {
        return ResponseEntity.ok(transactionService.update(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete transaction", description = "delete a transaction by its id.")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.ok("Deleted transaction with id " + id);
    }

    @GetMapping("{id}")
    @Operation(summary = "find transaction by id", description = "retrieve a transaction by its id.")
    public ResponseEntity<TransactionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getResponseById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "find all transactions", description = "list all transactions with pagination.")
    public ResponseEntity<Page<TransactionResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(transactionService.findAll(pageable));
    }

    @GetMapping("/all-by-user-id")
    @PreAuthorize("hasAnyRole('EXPERT') and authentication.principal.status == 'ACCEPT'")
    @Operation(summary = "find all transactions.",
            description = "find all transactions by CurrentUser id")
    public ResponseEntity<Page<TransactionResponse>> findAllByCurrentUser(
            @AuthenticationPrincipal Person userDetails,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity
                .ok(transactionService.findByUserId(userDetails.getId(), userDetails.getId(), PageRequest.of(page, size)));
    }

}
