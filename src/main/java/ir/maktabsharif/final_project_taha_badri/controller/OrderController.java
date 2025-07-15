package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateOrder;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/orders")
@RequiredArgsConstructor
@Tag(name = "OrderController", description = "Controller for Order.")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save")
    @Operation(summary = "save order.", description = "create a new order with initial status WAITING_FOR_EXPERT_PROPOSAL.")
    public ResponseEntity<Order> addOrder(
            @RequestBody @Validated(ValidationGroup.Save.class) SaveOrUpdateOrder dto) {
        return ResponseEntity.ok(orderService.save(dto));
    }

    @PutMapping("/update")
    @Operation(summary = "update order.", description = "update order fields before assignment or completion.")
    public ResponseEntity<Order> updateOrder(
            @RequestBody @Validated(ValidationGroup.Update.class) SaveOrUpdateOrder dto) {
        return ResponseEntity.ok(orderService.update(dto));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "delete order.", description = "delete method for order.")
    public ResponseEntity<String> deleteOrder(@RequestParam Long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok("Deleted order with id " + id);
    }

    @GetMapping("/find-by-id")
    @Operation(summary = "find order by id.", description = "retrieve an order by its ID.")
    public ResponseEntity<Order> findById(@RequestParam Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/find-all")
    @Operation(summary = "find all orders by page and size", description = "list orders with pagination.")
    public ResponseEntity<List<Order>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(orderService.findAll(page, size));
    }

    @GetMapping("/by-expert")
    @Operation(summary = "find all orders by expert", description = "list all orders assigned to a given expert.")
    public ResponseEntity<List<Order>> findAllByExpert(
            @RequestParam Long expertId) {
        return ResponseEntity.ok(orderService.findAllOrderByExpertId(expertId));
    }

    @GetMapping("/exists-by-expert-and-status")
    @Operation(summary = "check existence by expert and statuses",
            description = "return true if any order for the expert is in one of the given statuses.")
    public ResponseEntity<Boolean> existsByExpertAndStatuses(
            @RequestParam Long expertId,
            @RequestParam List<OrderStatus> statuses) {
        return ResponseEntity.ok(
                orderService.existsByExpertIdtAndOrderStatusIn(expertId, statuses)
        );
    }

    @PutMapping("/set-done")
    @Operation(summary = "mark order as DONE",
            description = "transition order to DONE; applies forfeiture logic and updates expert status/score.")
    public ResponseEntity<String> setOrderDone(@RequestParam Long orderId) {
        orderService.setOrderStatusToDone(orderId);
        return ResponseEntity.ok("Order " + orderId + " marked as DONE");
    }
}

