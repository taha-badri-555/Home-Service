package ir.maktabsharif.final_project_taha_badri.service.order;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.OrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.SearchOrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.OrderResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService extends BaseService<Order, Long, OrderRequest, OrderResponse> {
    OrderResponse getByIdForManager(Long orderId);

    OrderResponse update(Long customerId, OrderRequest dto);

    OrderResponse save(Long customerId, OrderRequest dto);

    Page<OrderResponse> findAllOrderByExpertId(Long expertId, Pageable pageable);

    boolean existsByExpertIdtAndOrderStatusIn(Long expert, List<OrderStatus> statuses);

    void setOrderStatusToDone(Long customer, Long orderId);

    Page<OrderResponse> findAllOrdersByExpert_Id(Long expertId, Pageable pageable);

    Page<SearchOrderRequest> searchOrders(SearchOrderRequest request, Pageable pageable);

    Page<OrderResponse> getAllByStatuse(Long customerId, OrderStatus status, Pageable pageable);
}
