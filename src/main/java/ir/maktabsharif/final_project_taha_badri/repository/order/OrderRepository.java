package ir.maktabsharif.final_project_taha_badri.repository.order;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findAllOrderByExpertId(Long expertId);

    boolean existsByExpertAndOrderStatusIn(Expert expert, List<OrderStatus> statuses);

    @Modifying
    @Query("""
            update Order o set o.orderStatus=:orderStatuse where o.id=:orderId
            """)
    void setOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") OrderStatus orderStatus);


}
