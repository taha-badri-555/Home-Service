package ir.maktabsharif.final_project_taha_badri.service.order;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateFeedback;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateOrder;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateExpert;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.OrderMapper;
import ir.maktabsharif.final_project_taha_badri.repository.order.OrderRepository;
import ir.maktabsharif.final_project_taha_badri.service.address.AddressService;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.feedback.FeedbackService;
import ir.maktabsharif.final_project_taha_badri.service.home_service.ServiceService;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerService;
import ir.maktabsharif.final_project_taha_badri.service.user.expert.ExpertService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl
        extends BaseServiceImpl<
        Order,
        Long,
        OrderRepository,
        SaveOrUpdateOrder,
        OrderMapper>
        implements OrderService {
    private final ExpertService expertService;
    private final AddressService addressService;
    private final CustomerService customerService;
    private final ServiceService serviceService;
    private final FeedbackService feedbackService;


    public OrderServiceImpl(
            OrderRepository repository,
            OrderMapper mapper,
            ExpertService expertService,
            AddressService addressService,
            CustomerService customerService,
            ServiceService serviceService,
            FeedbackService feedbackService) {
        super(repository, mapper);
        this.expertService = expertService;
        this.addressService = addressService;
        this.customerService = customerService;
        this.serviceService = serviceService;
        this.feedbackService = feedbackService;
    }

    protected void setEntityRelations(Order entity, SaveOrUpdateOrder dto) {
        if (dto.serviceId() != null) {
            entity.setService(serviceService.findById(dto.serviceId()));
        }
        if (dto.expertId() != null) {
            entity.setExpert(expertService.findById(dto.expertId()));
        }
        if (dto.addressId() != null) {
            entity.setAddress(addressService.findById(dto.addressId()));
        }
        if (dto.customerId() != null) {
            entity.setCustomer(customerService.findById(dto.customerId()));
        }

    }

    @Override
    public Order save(SaveOrUpdateOrder dto) {
        Order order = mapper.dtoToEntity(dto);
        if (order.getProposedPrice() < order.getService().getBasePrice()) {
            throw new IllegalArgumentException("Proposed price is lower than service price");
        }
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_PROPOSAL);
        setEntityRelations(order, dto);
            repository.save(order);
            return order;

    }

    @Override
    public List<Order> findAllOrderByExpertId(Long expertId) {
        return repository.findAllOrderByExpertId(expertId);
    }


    @Override
    public boolean existsByExpertIdtAndOrderStatusIn(Long expertId, List<OrderStatus> statuses) {

        Expert expert = expertService.findById(expertId);
        return repository.existsByExpertAndOrderStatusIn(expert, statuses);

    }

    @Override
    public void setOrderStatusToDone(Long orderId) {
        Order order = findById(orderId);
        Expert expert = order.getExpert();
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime endDate = order.getEndDate();
        Duration duration = Duration.between(endDate, now);
        Long hours = duration.toHours();
        if (hours > 0) {
            Byte score = (byte) (hours * -1);
            feedbackService.save(new SaveOrUpdateFeedback(
                    null,
                    score,
                    "FORFEITURE",
                    order.getId()));
            Double scoresAVGByExpertId = feedbackService.getScoresAVGByExpertId(expert.getId());
            if (scoresAVGByExpertId < 0) {
                expertService.update(new SaveOrUpdateExpert(expert.getId(), ExpertStatus.CHIR_FAAL));
            } else {
                expertService.update(new SaveOrUpdateExpert(expert.getId(), scoresAVGByExpertId));
            }

        }
        repository.setOrderStatus(orderId, OrderStatus.DONE);
    }
}
