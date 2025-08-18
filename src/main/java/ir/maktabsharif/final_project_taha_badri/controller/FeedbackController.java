package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.FeedbackRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.FeedbackResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.FeedbackMapper;
import ir.maktabsharif.final_project_taha_badri.service.feedback.FeedbackService;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
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
@RequestMapping(path = "/v1/feedbacks")
@RequiredArgsConstructor
@Tag(name = "FeedbackController", description = "Controller for Feedback.")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final FeedbackMapper mapper;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "save feedback.", description = "save method for feedback.")
    public ResponseEntity<FeedbackResponse> addFeedback(
            @AuthenticationPrincipal Person userDetails,
            @RequestBody @Validated(ValidationGroup.Save.class) FeedbackRequest dto) {
        return ResponseEntity.ok(feedbackService.save(userDetails.getId(), dto));
    }

    @PutMapping
    @Operation(summary = "update feedback.", description = "update method for feedback.")
    public ResponseEntity<FeedbackResponse> updateFeedback(
            @RequestBody @Validated(ValidationGroup.Update.class) FeedbackRequest dto) {
        return ResponseEntity.ok(feedbackService.update(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete feedback.", description = "delete method for feedback.")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteById(id);
        return ResponseEntity.ok("Deleted feedback with id " + id);
    }

    @GetMapping("{id}")
    @Operation(summary = "find feedback by id.", description = "find method for feedback based on id.")
    public ResponseEntity<FeedbackResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getResponseById(id));
    }

    @GetMapping
    @Operation(
            summary = "find all feedbacks by page number and page size.",
            description = "(find All) method for feedbacks based on page number and page size.")
    public ResponseEntity<Page<FeedbackResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(feedbackService.findAll(pageable));
    }

    @GetMapping("/avg-score")
    @PreAuthorize("hasRole('EXPERT') and authentication.principal.status == 'ACCEPT'")
    @Operation(summary = "get average score by expert.", description = "calculate the average feedback score for a given expert id.")
    public ResponseEntity<Double> getAverageScore(@AuthenticationPrincipal Person userDetails) {
        return ResponseEntity.ok(feedbackService.getScoresAVGByExpertId(userDetails.getId()));
    }

    @GetMapping("/score")
    @PreAuthorize("hasAnyRole('EXPERT') and authentication.principal.status ==" +
            " T(ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus).ACCEPT")
    @Operation(summary = "get score by order and expert.", description = "retrieve the feedback score for a specific order and expert.")
    public ResponseEntity<Byte> getScoreByOrderAndExpert(
            @RequestParam Long orderId,
            @AuthenticationPrincipal Person userDetails) {
        return ResponseEntity.ok(feedbackService.getScoreByOrderAndExpertId(orderId, userDetails.getId()));
    }

    @GetMapping("/by-order-and-expert")
    @Operation(summary = "find feedback by order and expert.", description = "find a Feedback entity by orderId and expertId.")
    public ResponseEntity<Feedback> findByOrderAndExpert(
            @RequestParam Long orderId,
            @RequestParam Long expertId) {
        return ResponseEntity.ok(feedbackService.findByOrderIdAndExpertId(orderId, expertId));
    }

    @GetMapping("/total-score")
    @Operation(summary = "get total score by expert.", description = "sum of all feedback scores for a given expert id.")
    public ResponseEntity<Long> getTotalScore(@RequestParam Long expertId) {
        return ResponseEntity.ok(feedbackService.getAllScoreByExpertId(expertId));
    }
}

