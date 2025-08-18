package ir.maktabsharif.final_project_taha_badri.service.order;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.*;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.ExpertRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.OrderResponse;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.CustomerResponse;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.ExpertResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.OrderMapper;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.ExpertMapper;
import ir.maktabsharif.final_project_taha_badri.repository.order.OrderRepository;
import ir.maktabsharif.final_project_taha_badri.repository.order.OrderSpecifications;
import ir.maktabsharif.final_project_taha_badri.service.address.AddressService;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.feedback.FeedbackService;
import ir.maktabsharif.final_project_taha_badri.service.home_service.ServiceService;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerService;
import ir.maktabsharif.final_project_taha_badri.service.user.expert.ExpertService;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl
        extends BaseServiceImpl<
        Order,
        Long,
        OrderRepository,
        OrderRequest,
        OrderResponse,
        OrderMapper>
        implements OrderService {
    private final ExpertMapper expertMapper;
    private final ExpertService expertService;
    private final AddressService addressService;
    private final CustomerService customerService;
    private final ServiceService serviceService;
    private final FeedbackService feedbackService;


    public OrderServiceImpl(
            OrderRepository repository,
            OrderMapper mapper, ExpertMapper expertMapper,
            ExpertService expertService,
            AddressService addressService,
            CustomerService customerService,
            ServiceService serviceService,
            FeedbackService feedbackService) {
        super(repository, mapper);
        this.expertMapper = expertMapper;
        this.expertService = expertService;
        this.addressService = addressService;
        this.customerService = customerService;
        this.serviceService = serviceService;
        this.feedbackService = feedbackService;
    }

    @NotNull
    private static ExpertResponse getExpertDto(Order order) {
        return new ExpertResponse(
                order.getExpert().getCreateDate(), order.getExpert().getLastUpdateDate(),
                order.getExpert().getId(),
                order.getExpert().getFirstName(), order.getExpert().getLastName(),
                order.getExpert().getEmail(),
                order.getExpert().getScore(), order.getExpert().getStatus(), order.getExpert().getAvgScore());
    }

    @NotNull
    private static AddressRequest getAddressDto(Order order) {
        return new AddressRequest(
                order.getAddress().getId(), order.getAddress().getProvince(),
                order.getAddress().getCity(), order.getAddress().getDetail(),
                order.getAddress().getCustomer().getId());
    }

    @NotNull
    private static ServiceRequest getServiceDto(Order order) {
        return new ServiceRequest(
                order.getService().getId(), order.getService().getName(),
                order.getService().getBasePrice(), order.getService().getDescription(),
                order.getService().getParentService().getId());
    }

    @NotNull
    private static CustomerResponse getCustomerRequest(Order order) {
        return new CustomerResponse(
                order.getCustomer().getId(), order.getCustomer().getCreateDate(),
                order.getCustomer().getLastUpdateDate(), order.getCustomer().getFirstName(),
                order.getCustomer().getLastName(), order.getCustomer().getEmail(), order.getAddress().getId());
    }


    @Override
    public boolean existsByExpertIdtAndOrderStatusIn(Long expertId, List<OrderStatus> statuses) {

        Expert expert = expertService.findById(expertId);
        return repository.existsByExpertAndOrderStatusIn(expert, statuses);

    }

    protected void setEntityRelations(Order entity, OrderRequest dto) {
        if (dto.serviceId() != null) {
            entity.setService(serviceService.findById(dto.serviceId()));
        }
        if (dto.expertId() != null) {
            entity.setExpert(expertService.findById(dto.expertId()));
        }
        if (dto.addressId() != null) {
            entity.setAddress(addressService.findById(dto.addressId()));
        }

    }

    @Override
    public OrderResponse save(Long customerId, OrderRequest dto) {
        Order order = mapper.requestToEntity(dto);
        setEntityRelations(order, dto);
        order.setCustomer(customerService.findById(customerId));
        if (order.getProposedPrice() < order.getService().getBasePrice()) {
            throw new IllegalArgumentException("Proposed price is lower than service price");
        }
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_PROPOSAL);
        repository.save(order);
        return mapper.entityToResponse(order);

    }

    @Override
    public OrderResponse update(Long customerId, OrderRequest dto) {
        Order byId = findById(dto.id());
        mapper.updateEntityWithRequest(dto, byId);
        byId.setCustomer(customerService.findById(customerId));
        repository.save(byId);
        return mapper.entityToResponse(byId);
    }

    @Override
    public Page<OrderResponse> findAllOrderByExpertId(Long expertId, Pageable pageable) {
        Page<Order> allOrder = repository.findAllOrderByExpertId(expertId, pageable);

        List<OrderResponse> dtoList = allOrder.getContent().stream()
                .map(mapper::entityToResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, allOrder.getTotalElements());
    }

    @Override
    public void setOrderStatusToDone(Long customerId, Long orderId) {
        Order byId = findById(orderId);
        if (!Objects.equals(customerId, byId.getCustomer().getId())) {
            throw new IllegalArgumentException("This order has not for customer " + customerId);
        }
        Order order = findById(orderId);
        Expert expert = order.getExpert();
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime endDate = order.getEndDate();
        Duration duration = Duration.between(endDate, now);
        long hours = duration.toHours();
        if (hours < 0) {
            Byte score = (byte) (hours);
            feedbackService.save(new FeedbackRequest(
                    null,
                    score,
                    "FORFEITURE",
                    order.getId()));
            Double avgScore = feedbackService.getScoresAVGByExpertId(expert.getId());
            expert.setAvgScore(avgScore);
            expertService.update(expert.getId(), expertMapper.entityToRequest(expert));
            Double scoresAVGByExpertId = feedbackService.getScoresAVGByExpertId(expert.getId());
            expert.setAvgScore(scoresAVGByExpertId);
            expertService.update(expert.getId(), expertMapper.entityToRequest(expert));


            if (scoresAVGByExpertId != null && scoresAVGByExpertId < 0) {
                expertService.update(expert.getId(), new ExpertRequest(ExpertStatus.DISABLED));
            }

            if (avgScore < 0) {

                expertService.update(expert.getId(), new ExpertRequest(ExpertStatus.DISABLED));
            } else {
                expertService.update(expert.getId(), new ExpertRequest(avgScore));
            }

        }
        repository.setOrderStatus(orderId, OrderStatus.DONE);
    }

    @Override
    public Page<OrderResponse> findAllOrdersByExpert_Id(Long expertId, Pageable pageable) {
        return repository.findAllOrdersByExpert_Id(expertId, pageable)
                .map(mapper::entityToResponse);
    }


    @Override
    public Page<SearchOrderRequest> searchOrders(SearchOrderRequest request, Pageable pageable) {
        Specification<Order> spe = combineSpecs(
                OrderSpecifications.hasUserRole(request.role()),
                OrderSpecifications.hasStatus(request.status()),
                OrderSpecifications.hasDateBetween(request.startDate(), request.endDate()),
                OrderSpecifications.hasService(request.serviceName()));
        return repository.findAll(spe, pageable)
                .map(order -> new SearchOrderRequest(
                        order.getId(),
                        request.role(),
                        order.getStartDate(),
                        order.getEndDate(),
                        order.getOrderStatus(),
                        order.getService().getName()));
    }

    @Override
    public OrderResponse getByIdForManager(Long orderId) {
        Order order = super.findById(orderId);
        return createOrderRequest(order);
    }

    private OrderResponse createOrderRequest(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .createDate(order.getCreateDate())
                .lastUpdateDate(order.getLastUpdateDate())
                .description(order.getDescription())
                .proposedPrice(order.getProposedPrice())
                .startDate(order.getStartDate())
                .orderStatus(order.getOrderStatus())
                .serviceName(order.getService().getName())
                .province(order.getAddress().getProvince())
                .city(order.getAddress().getCity())
                .detail(order.getAddress().getDetail())
                .customerName(order.getCustomer().getFirstName())
                .customerLastName(order.getCustomer().getLastName())
                .customerEmail(order.getCustomer().getEmail())
                .build();
    }


    @SafeVarargs
    private Specification<Order> combineSpecs(Specification<Order>... specs) {
        Specification<Order> result = null;
        for (Specification<Order> spec : specs) {
            if (spec != null) {
                result = (result == null) ? spec : result.and(spec);
            }
        }
        return result;
    }

    @Override
    public Page<OrderResponse> getAllByStatuse(Long customerId, OrderStatus status, Pageable pageable) {
        Specification<Order> spec = OrderSpecifications.hasCustomerId(customerId);
        if (status != null) {
            spec = spec.and(OrderSpecifications.hasStatus(status));
        }
        Sort sort = (status == null)
                ? Sort.by("orderStatus").ascending()
                : pageable.getSort();

        Page<Order> orders = repository.findAll(spec,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));

        return orders.map(this::createOrderRequest);
    }
}
