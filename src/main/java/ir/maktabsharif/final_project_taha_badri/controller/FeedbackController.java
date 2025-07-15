package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateFeedback;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import ir.maktabsharif.final_project_taha_badri.service.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/feedbacks")
@RequiredArgsConstructor
@Tag(name = "FeedbackController", description = "Controller for Feedback.")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/save")
    @Operation(summary = "save feedback.", description = "save method for feedback.")
    public ResponseEntity<Feedback> addFeedback(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateFeedback dto) {
        return ResponseEntity.ok(feedbackService.save(dto));
    }

    @PutMapping("/update")
    @Operation(summary = "update feedback.", description = "update method for feedback.")
    public ResponseEntity<Feedback> updateFeedback(
            @RequestBody @Validated(ValidationGroup.Update.class) SaveOrUpdateFeedback dto) {
        return ResponseEntity.ok(feedbackService.update(dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete feedback.", description = "delete method for feedback.")
    public ResponseEntity<String> deleteFeedback(@RequestParam Long id) {
        feedbackService.deleteById(id);
        return ResponseEntity.ok("Deleted feedback with id " + id);
    }

    @GetMapping("/find-by-id")
    @Operation(summary = "find feedback by id.", description = "find method for feedback based on id.")
    public ResponseEntity<Feedback> findById(@RequestParam Long id) {
        return ResponseEntity.ok(feedbackService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(
            summary = "find all feedbacks by page number and page size.",
            description = "(find All) method for feedbacks based on page number and page size.")
    public ResponseEntity<List<Feedback>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(feedbackService.findAll(page, size));
    }

    @GetMapping("/avg-score")
    @Operation(summary = "get average score by expert.", description = "calculate the average feedback score for a given expert id.")
    public ResponseEntity<Double> getAverageScore(@RequestParam Long expertId) {
        return ResponseEntity.ok(feedbackService.getScoresAVGByExpertId(expertId));
    }

    @GetMapping("/score")
    @Operation(summary = "get score by order and expert.", description = "retrieve the feedback score for a specific order and expert.")
    public ResponseEntity<Byte> getScoreByOrderAndExpert(
            @RequestParam Long orderId,
            @RequestParam Long expertId) {
        return ResponseEntity.ok(feedbackService.getScoreByOrderAndExpertId(orderId, expertId));
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

