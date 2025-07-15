package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateTransaction;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Transaction;
import ir.maktabsharif.final_project_taha_badri.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/transactions")
@RequiredArgsConstructor
@Tag(name = "TransactionController", description = "Controller for Transaction.")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/save")
    @Operation(summary = "save transaction", description = "create a new transaction (expert-customer payment).")
    public ResponseEntity<Transaction> addTransaction(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateTransaction dto) {
        return ResponseEntity.ok(transactionService.save(dto));
    }

    @PutMapping("/update")
    @Operation(summary = "update transaction", description = "update an existing transaction.")
    public ResponseEntity<Transaction> updateTransaction(
            @RequestBody @Validated(ValidationGroup.Update.class) SaveOrUpdateTransaction dto) {
        return ResponseEntity.ok(transactionService.update(dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete transaction", description = "delete a transaction by its id.")
    public ResponseEntity<String> deleteTransaction(@RequestParam Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.ok("Deleted transaction with id " + id);
    }

    @GetMapping("/find-by-id")
    @Operation(summary = "find transaction by id", description = "retrieve a transaction by its id.")
    public ResponseEntity<Transaction> findById(@RequestParam Long id) {
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "find all transactions", description = "list all transactions with pagination.")
    public ResponseEntity<List<Transaction>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(transactionService.findAll(page, size));
    }

    @GetMapping("/by-customer")
    @Operation(summary = "find transactions by customer", description = "list all transactions for a given customer id.")
    public ResponseEntity<List<Transaction>> findAllByCustomer(@RequestParam Long customerId) {
        return ResponseEntity.ok(transactionService.findAllByCustomer(customerId));
    }

    @GetMapping("/by-expert")
    @Operation(summary = "find transactions by expert", description = "list all transactions for a given expert id.")
    public ResponseEntity<List<Transaction>> findAllByExpert(@RequestParam Long expertId) {
        return ResponseEntity.ok(transactionService.findAllByExpert(expertId));
    }
}
