package ir.maktabsharif.final_project_taha_badri.repository.order;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Page<Order> findAllOrderByExpertId(Long expertId,Pageable pageable);

    boolean existsByExpertAndOrderStatusIn(Expert expert, List<OrderStatus> statuses);
    @Modifying
    @Query("""
             update Order o set o.orderStatus = :orderStatus where o.id = :orderId
            """)
    void setOrderStatus(@Param("orderId") Long orderId, @Param("orderStatus") OrderStatus orderStatus);

    @Query("""
                select o from Order o where o.expert.id = :expertId
            """)
    Page<Order> findAllOrdersByExpert_Id(@Param("expertId") Long expertId, Pageable pageable);
}
