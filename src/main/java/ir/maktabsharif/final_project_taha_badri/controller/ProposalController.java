package ir.maktabsharif.final_project_taha_badri.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.OrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateProposal;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ProposalStatus;
import ir.maktabsharif.final_project_taha_badri.service.proposal.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/proposals")
@RequiredArgsConstructor
@Tag(name = "ProposalController", description = "Controller for Proposal.")
public class ProposalController {
    private final ProposalService proposalService;

    @PostMapping("/save")
    @Operation(summary = "save proposal", description = "submit a new proposal by expertId")
    public ResponseEntity<Proposal> addProposal(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateProposal dto,
            @RequestParam Long expertId) {
        return ResponseEntity.ok(proposalService.save(dto, expertId));
    }

    @PutMapping("/update")
    @Operation(summary = "update proposal", description = "update an existing proposal")
    public ResponseEntity<Proposal> updateProposal(
            @RequestBody @Validated(ValidationGroup.Update.class) SaveOrUpdateProposal dto) {
        return ResponseEntity.ok(proposalService.update(dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete proposal", description = "delete a proposal by its id")
    public ResponseEntity<String> deleteProposal(@RequestParam Long id) {
        proposalService.deleteById(id);
        return ResponseEntity.ok("Deleted proposal with id " + id);
    }

    @GetMapping("/find-by-id")
    @Operation(summary = "find proposal by id", description = "retrieve a proposal by its id")
    public ResponseEntity<Proposal> findById(@RequestParam Long id) {
        return ResponseEntity.ok(proposalService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "find all proposals", description = "list all proposals with pagination")
    public ResponseEntity<List<Proposal>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(proposalService.findAll(page, size));
    }

    @GetMapping("/by-order")
    @Operation(summary = "find proposals by order", description = "list proposals for an order sorted by price")
    public ResponseEntity<List<Proposal>> findByOrderOrderByPrice(
            @RequestParam Long orderId) {
        return ResponseEntity.ok(proposalService.findByOrderIdOrderBySuggestPrice(orderId));
    }

    @GetMapping("/by-order/expert-score")
    @Operation(summary = "find proposals by order", description = "list proposals for an order sorted by expert score")
    public ResponseEntity<List<Proposal>> findByOrderOrderByExpertScore(
            @RequestParam Long orderId) {
        return ResponseEntity.ok(proposalService.findByOrderIdOrderByExpertScoreDesc(orderId));
    }

    @PostMapping("/set-order-with-proposal")
    @Operation(summary = "attach proposal to order", description = "assign the given proposal to its order")
    public ResponseEntity<String> setOrderWithProposal(@RequestParam Long proposalId) {
        proposalService.setOrderWithProposal(proposalId);
        return ResponseEntity.ok("Order associated with proposal " + proposalId);
    }

    @PutMapping("/start-by-proposal")
    @Operation(summary = "start order by proposal", description = "mark order as STARTED with startTime now")
    public ResponseEntity<String> setOrderStarted(@RequestParam Long proposalId) {
        proposalService.setOrderStatusToStartedByProposalId(proposalId);
        return ResponseEntity.ok("Order started for proposal " + proposalId);
    }

    @PutMapping("/change-status")
    @Operation(summary = "change proposal status", description = "update status of one proposal")
    public ResponseEntity<String> changeStatus(
            @RequestParam Long proposalId,
            @RequestParam ProposalStatus status) {
        proposalService.changeStatusById(proposalId, status);
        return ResponseEntity.ok("Proposal " + proposalId + " status set to " + status);
    }

    @PutMapping("/change-all-status")
    @Operation(summary = "change all proposals status", description = "bulk update status for all proposals of an order")
    public ResponseEntity<String> changeAllStatus(
            @RequestParam Long orderId,
            @RequestParam ProposalStatus status) {
        proposalService.changeAllProposalStatusByOrderId(orderId, status);
        return ResponseEntity.ok("All proposals for order " + orderId + " set to " + status);
    }

    @PostMapping("/choose")
    @Operation(summary = "choose proposal", description = "accept one proposal and reject others for an order")
    public ResponseEntity<String> chooseProposal(
            @RequestParam Long proposalId,
            @RequestParam Long orderId) {
        proposalService.chooseProposal(proposalId, orderId);
        return ResponseEntity.ok("Proposal " + proposalId + " accepted for order " + orderId);
    }

    @GetMapping("/orders-by-expert")
    @Operation(summary = "list orders by expert", description = "get all order requests and accepted orders for an expert")
    public ResponseEntity<List<OrderRequest>> ordersByExpert(@RequestParam Long expertId) {
        return ResponseEntity.ok(proposalService.findAllOrdersByExpert_Id(expertId));
    }
}

