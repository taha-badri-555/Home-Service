package ir.maktabsharif.final_project_taha_badri.service.order;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateOrder;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.OrderMapper;
import ir.maktabsharif.final_project_taha_badri.repository.order.OrderRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class OrderServiceImpl
        extends BaseServiceImpl<
        Order,
        Long,
        OrderRepository,
        OrderMapper,
        SaveOrUpdateOrder>
        implements OrderService {

    public OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Order saveOrUpdateProposal(SaveOrUpdateOrder dto) {
        Order order = mapper.dtoToEntity(dto);
        if (order.getProposedPrice() < order.getService().getBasePrice()) {

            throw new IllegalArgumentException("Proposed price is lower than service price");
        } else {
            repository.beginTransaction();
            repository.save(order);
            repository.commitTransaction();
            return order;
        }
    }
}
