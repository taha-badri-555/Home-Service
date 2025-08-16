package ir.maktabsharif.final_project_taha_badri.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.OrderResponse;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ProposalRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.ProposalResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ProposalStatus;
import ir.maktabsharif.final_project_taha_badri.service.proposal.ProposalService;
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
@RequestMapping(path = "/v1/proposals")
@RequiredArgsConstructor
@Tag(name = "ProposalController", description = "Controller for Proposal.")
public class ProposalController {
    private final ProposalService proposalService;


    @PostMapping
    @PreAuthorize("hasAnyRole('EXPERT')")
    @Operation(summary = "save proposal", description = "submit a new proposal by expertId")
    public ResponseEntity<ProposalResponse> addProposal(
            @AuthenticationPrincipal Person userDetails,
            @RequestBody @Validated(ValidationGroup.Save.class) ProposalRequest dto
    ) {
        return ResponseEntity.ok(proposalService.save(userDetails.getId(), dto));
    }

    @PutMapping
    @Operation(summary = "update proposal", description = "update an existing proposal")
    public ResponseEntity<ProposalResponse> updateProposal(
            @RequestBody @Validated(ValidationGroup.Update.class) ProposalRequest dto) {
        return ResponseEntity.ok(proposalService.update(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete proposal", description = "delete a proposal by its id")
    public ResponseEntity<String> deleteProposal(@PathVariable Long id) {
        proposalService.deleteById(id);
        return ResponseEntity.ok("Deleted proposal with id " + id);
    }

    @GetMapping("{id}")
    @Operation(summary = "find proposal by id", description = "retrieve a proposal by its id")
    public ResponseEntity<ProposalResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(proposalService.getResponseById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "find all proposals", description = "list all proposals with pagination")
    public ResponseEntity<Page<ProposalResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(proposalService.findAll(pageable));
    }

    @GetMapping("find-by-order/{orderId}")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "Find proposals by order", description = "List proposals for an order sorted by price")
    public ResponseEntity<Page<ProposalResponse>> findByOrderOrderByPrice(
            @AuthenticationPrincipal Person userDetails,
            @PathVariable Long orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(proposalService.findByOrderIdOrderBySuggestPrice(userDetails.getId(), orderId, pageable));
    }


    @GetMapping("/by-expert-score")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "Find proposals by order", description = "List proposals for an order sorted by expert score")
    public ResponseEntity<Page<ProposalResponse>> findByOrderOrderByExpertScore(
            @AuthenticationPrincipal Person userDetails,
            @RequestParam Long orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProposalResponse> proposals = proposalService.findByOrderIdOrderByExpertScoreDesc(userDetails.getId(), orderId, pageable);
        return ResponseEntity.ok(proposals);
    }


    @PutMapping("/with-proposal")
    @Operation(summary = "attach proposal to order", description = "assign the given proposal to its order")
    public ResponseEntity<String> setOrderWithProposal(@RequestParam Long proposalId) {
        proposalService.setOrderWithProposal(proposalId);
        return ResponseEntity.ok("Order associated with proposal " + proposalId);
    }

    @PutMapping("/start-by-proposal")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "start order by proposal", description = "mark order as STARTED with startTime now")
    public ResponseEntity<String> setOrderStarted(
            @AuthenticationPrincipal Person userDetails,
            @RequestParam Long proposalId) {
        proposalService.setOrderStatusToStartedByProposalId(userDetails.getId(), proposalId);
        return ResponseEntity.ok("Order started for proposal " + proposalId);
    }

    @PutMapping("/status")
    @Operation(summary = "change proposal status", description = "update status of one proposal")
    public ResponseEntity<String> changeStatus(
            @RequestParam Long proposalId,
            @RequestParam ProposalStatus status) {
        proposalService.changeStatusById(proposalId, status);
        return ResponseEntity.ok("Proposal " + proposalId + " status set to " + status);
    }

    @PutMapping("/all-status")
    @Operation(summary = "change all proposals status", description = "bulk update status for all proposals of an order")
    public ResponseEntity<String> changeAllStatus(
            @RequestParam Long orderId,
            @RequestParam ProposalStatus status) {
        proposalService.changeAllProposalStatusByOrderId(orderId, status);
        return ResponseEntity.ok("All proposals for order " + orderId + " set to " + status);
    }

    @PutMapping("/choose")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "choose proposal", description = "accept one proposal and reject others for an order")
    public ResponseEntity<String> chooseProposal(
            @AuthenticationPrincipal Person userDetails,
            @RequestParam Long proposalId,
            @RequestParam Long orderId) {
        proposalService.chooseProposal(userDetails.getId(),orderId , proposalId);
        return ResponseEntity.ok("Proposal " + proposalId + " accepted for order " + orderId);
    }

    @GetMapping("/by-expert")
    @PreAuthorize("hasAnyRole('EXPERT') and authentication.principal.status == 'ACCEPT'")
    @Operation(summary = "find all orders with expert ID", description = "find all orders with expert send proposal.")
    public ResponseEntity<Page<OrderResponse>> ordersByExpert(
            @AuthenticationPrincipal Person userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderResponse> orders = proposalService.findAllOrdersByExpert_Id(userDetails.getId(), pageable);
        return ResponseEntity.ok(orders);
    }

}

