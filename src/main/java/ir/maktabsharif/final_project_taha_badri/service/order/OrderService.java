package ir.maktabsharif.final_project_taha_badri.service.order;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateOrder;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service

public interface OrderService extends BaseService<Order, Long, SaveOrUpdateOrder> {
    Order saveOrUpdateProposal(SaveOrUpdateOrder dto);
}
