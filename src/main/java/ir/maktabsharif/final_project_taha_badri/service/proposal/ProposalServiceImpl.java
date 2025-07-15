package ir.maktabsharif.final_project_taha_badri.service.proposal;

import ir.maktabsharif.final_project_taha_badri.domain.dto.AcceptedOrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.OrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateOrder;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateProposal;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ProposalStatus;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.OrderMapper;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.ProposalMapper;
import ir.maktabsharif.final_project_taha_badri.exception.InvalidRequestException;
import ir.maktabsharif.final_project_taha_badri.exception.UnverifiedExpertException;
import ir.maktabsharif.final_project_taha_badri.repository.proposal.ProposalRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.order.OrderService;
import ir.maktabsharif.final_project_taha_badri.service.user.expert.ExpertService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class ProposalServiceImpl
        extends BaseServiceImpl<
        Proposal,
        Long,
        ProposalRepository,
        SaveOrUpdateProposal,
        ProposalMapper>
        implements ProposalService {
    private final ExpertService expertService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public ProposalServiceImpl(
            ProposalRepository repository, ProposalMapper mapper
            , ExpertService expertService,
            OrderService orderService,
            OrderMapper orderMapper1) {
        super(repository, mapper);
        this.expertService = expertService;
        this.orderMapper = orderMapper1;
        this.orderService = orderService;
    }

    protected void setEntityRelations(Proposal entity, SaveOrUpdateProposal dto) {
        if (dto.expertId() != null) {
            entity.setExpert(expertService.findById(dto.expertId()));
        }
        if (dto.orderId() != null) {
            entity.setOrder(orderService.findById(dto.orderId()));
        }

    }

    @Transactional
    @Override
    public Proposal save(SaveOrUpdateProposal saveOrUpdateProposal, Long expertId) {
        Proposal proposal = mapper.dtoToEntity(saveOrUpdateProposal);
        Order order = orderService.findById(saveOrUpdateProposal.orderId());
        proposal.setOrder(order);

        Expert expert = expertService.findById(expertId);
        if (!expert.getStatus().equals(ExpertStatus.ACCEPT)) {
            throw new UnverifiedExpertException();
        }
        Set<ir.maktabsharif.final_project_taha_badri.domain.entity.Service> services =
                new HashSet<>();
        services.add(order.getService());
        boolean hasService = expertService.existsByServices(services);

        if (!hasService) {
            throw new InvalidRequestException("Expert does not offer the service required by this proposal.");
        }

        OrderStatus status = order.getOrderStatus();
        if (!(status.equals(OrderStatus.WAITING_FOR_EXPERT_PROPOSAL) || status.equals(OrderStatus.WAITING_TO_CHOOSE_EXPERT))) {
            throw new InvalidRequestException("Order status does not allow proposal submission.");
        }
        proposal.setExpert(expert);

        if (status.equals(OrderStatus.WAITING_FOR_EXPERT_PROPOSAL)) {
            order.setOrderStatus(OrderStatus.WAITING_TO_CHOOSE_EXPERT);
            SaveOrUpdateOrder orderDto = orderMapper.entityToDto(order);
            orderService.save(orderDto);
        }
        Long hours = createDuration(proposal.getEndDate(), proposal.getEndDate());
        proposal.setDuration(hours);
        proposal.setStatus(ProposalStatus.PENDING);
        return repository.save(proposal);
    }

    @Override
    public Proposal update(SaveOrUpdateProposal dto) {
        if (dto.startWork() != null || dto.endDate() != null) {
            Long duration = createDuration(dto.startWork(), dto.startWork());
            Proposal proposal = mapper.dtoToEntity(dto);
            mapper.updateEntityWithDTO(dto, proposal);
            proposal.setDuration(duration);
            return repository.save(proposal);
        }
        return update(dto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Proposal> findByOrderIdOrderBySuggestPrice(Long orderId) {
        return repository.findByOrderIdOrderBySuggestPriceAsc(orderId);
    }

    @Transactional
    @Override
    public void setOrderWithProposal(Long proposalId) {
        repository.setOrderWithProposal(proposalId);

    }

    @Transactional(readOnly = true)
    @Override
    public List<Proposal> findByOrderIdOrderByExpertScoreDesc(Long orderId) {
        return repository.findByOrderIdOrderByExpertScoreDesc(orderId);
    }

    @Transactional
    @Override
    public void setOrderStatusToStartedByProposalId(Long proposalId) {
        repository.setOrderStatusToStartedByProposalId(proposalId, ZonedDateTime.now());


    }


    @Transactional
    @Override
    public void changeAllProposalStatusByOrderId(Long orderId, ProposalStatus status) {
        repository.changeAllProposalStatusByOrderId(orderId, status);
    }

    @Transactional
    @Override
    public void changeStatusById(Long proposalId, ProposalStatus status) {
        repository.changeStatusById(proposalId, status);
    }

    @Transactional
    @Override
    public void chooseProposal(Long proposalId, Long orderId) {
        this.setOrderWithProposal(proposalId);
        this.changeAllProposalStatusByOrderId(orderId, ProposalStatus.REJECTED);
        this.changeStatusById(proposalId, ProposalStatus.ACCEPTED);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderRequest> findAllOrdersByExpert_Id(Long expertId) {
        List<Order> orders = repository.findAllOrdersByExpert_Id(expertId);
        return orders.stream()
                .map(order -> {
                    if (Objects.equals(order.getExpert().getId(), expertId)) {
                        return new AcceptedOrderRequest(
                                order.getDescription(),
                                order.getProposedPrice(),
                                order.getStartDate(),
                                order.getOrderStatus(),
                                order.getService().getName(),
                                order.getAddress().getProvince(),
                                order.getAddress().getCity(),
                                order.getAddress().getDetail(),
                                order.getCustomer().getFirstName(),
                                order.getCustomer().getLastName(),
                                order.getCustomer().getEmail());
                    } else {
                        return new OrderRequest(
                                order.getDescription(),
                                order.getProposedPrice(),
                                order.getStartDate(),
                                order.getOrderStatus(),
                                order.getService().getName(),
                                order.getAddress().getProvince(),
                                order.getAddress().getCity(),
                                order.getAddress().getDetail());
                    }
                })
                .toList();
    }

    private Long createDuration(ZonedDateTime start, ZonedDateTime end) {
        Duration duration = Duration.between(start, end);
        return duration.toHours();
    }
}
