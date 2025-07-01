package ir.maktabsharif.final_project_taha_badri.repository.order;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class OrderRepositoryImpl
        extends CrudRepositoryImpl<Order, Long>
        implements OrderRepository {

    public OrderRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Order> getDomainClass() {
        return Order.class;
    }
}
