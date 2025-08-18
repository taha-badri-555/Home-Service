package ir.maktabsharif.final_project_taha_badri.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.OrderResponse;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.SearchOrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.OrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.OrderMapper;
import ir.maktabsharif.final_project_taha_badri.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/orders")
@RequiredArgsConstructor
@Tag(name = "OrderController", description = "Controller for Order.")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper mapper;


    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "save order.", description = "create a new order with initial status WAITING_FOR_EXPERT_PROPOSAL.")
    public ResponseEntity<OrderResponse> addOrder(
            @AuthenticationPrincipal Person userDetails,
            @RequestBody @Validated(ValidationGroup.Save.class) OrderRequest dto) {

        return ResponseEntity.ok(orderService.save(userDetails.getId(), dto));
    }


    @PutMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "update order.", description = "update order fields before assignment or completion.")
    public ResponseEntity<OrderResponse> updateOrder(
            @AuthenticationPrincipal Person userDetails,
            @RequestBody @Validated(ValidationGroup.Update.class) OrderRequest dto) {
        return ResponseEntity.ok(orderService.update(userDetails.getId(), dto));
    }

    @DeleteMapping
    @Operation(summary = "delete order.", description = "delete method for order.")
    public ResponseEntity<String> deleteOrder(@RequestParam Long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok("Deleted order with id " + id);
    }

    @GetMapping("{id}")
    @Operation(summary = "find order by id.", description = "retrieve an order by its ID.")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getResponseById(id));
    }

    @GetMapping
    @Operation(summary = "find all orders by page and size", description = "list orders with pagination.")
    public ResponseEntity<Page<OrderResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.findAll(pageable));
    }

    @GetMapping("/by-expert")
    @PreAuthorize("hasAnyRole('EXPERT') and authentication.principal.status ==" +
            " T(ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus).ACCEPT")
    @Operation(summary = "find all orders by expert", description = "list all orders assigned to a given expert.")
    public ResponseEntity<Page<OrderResponse>> findAllByExpert(
            @AuthenticationPrincipal Person userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.findAllOrderByExpertId(userDetails.getId(), pageable));
    }

    @GetMapping("/by-expert/{expertId}/status/{statuses}")
    @Operation(summary = "check existence by expert and statuses",
            description = "return true if any order for the expert is in one of the given statuses.")
    public ResponseEntity<Boolean> existsByExpertAndStatuses(
            @PathVariable Long expertId,
            @PathVariable List<OrderStatus> statuses) {
        return ResponseEntity.ok(
                orderService.existsByExpertIdtAndOrderStatusIn(expertId, statuses)
        );
    }

    @PutMapping("{orderId}")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "mark order as DONE",
            description = "transition order to DONE; applies forfeiture logic and updates expert status/score.")
    public ResponseEntity<String> setOrderDone(
            @AuthenticationPrincipal Person userDetails,
            @PathVariable Long orderId) {
        orderService.setOrderStatusToDone(userDetails.getId(), orderId);
        return ResponseEntity.ok("Order " + orderId + " marked as DONE");
    }

    @GetMapping("/orders-by-expert")
    @Operation(summary = "find all order by expertId.",
            description = "find all order by expertId at page. ")
    public ResponseEntity<Page<OrderResponse>> ordersByExpert(
            @RequestParam Long expertId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderResponse> orders = orderService.findAllOrdersByExpert_Id(expertId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Operation(
            summary = "Find orders summary",
            description = "Find all orders summary by optional filters"
    )
    public ResponseEntity<Page<SearchOrderRequest>> searchOrders(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endDate,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String serviceName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        SearchOrderRequest request =
                new SearchOrderRequest(
                        id, role,
                        startDate, endDate,
                        status, serviceName
                );

        Page<SearchOrderRequest> result = orderService.searchOrders(request, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/for-manager")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Operation(
            summary = "Find orders.",
            description = "Find order by order id for manager."
    )
    public ResponseEntity<OrderResponse> getOrdersForManager(
            @RequestParam Long orderId
    ) {
        return ResponseEntity.ok(orderService.getByIdForManager(orderId));
    }

    @GetMapping("/by-status")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(
            summary = "Find orders for customers.",
            description = "Find order by order status for customer."
    )
    public ResponseEntity<Page<OrderResponse>> getOrdersByStatus(
            @AuthenticationPrincipal Person userDetails,
            @RequestParam OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size

    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderResponse> allByStatus = orderService.getAllByStatuse(userDetails.getId(), status, pageable);
        return ResponseEntity.ok(allByStatus);
    }

}

