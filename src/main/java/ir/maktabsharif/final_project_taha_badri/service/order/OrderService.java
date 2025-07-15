package ir.maktabsharif.final_project_taha_badri.service.order;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateOrder;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService extends BaseService<Order, Long, SaveOrUpdateOrder> {

    List<Order> findAllOrderByExpertId(Long expertId);

    boolean existsByExpertIdtAndOrderStatusIn(Long expert, List<OrderStatus> statuses);

    void setOrderStatusToDone(Long orderId);
}
