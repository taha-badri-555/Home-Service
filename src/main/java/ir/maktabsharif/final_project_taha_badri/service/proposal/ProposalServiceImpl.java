package ir.maktabsharif.final_project_taha_badri.service.proposal;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.OrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ProposalRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.OrderResponse;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.ProposalResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ProposalStatus;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.ProposalMapper;
import ir.maktabsharif.final_project_taha_badri.exception.InvalidRequestException;
import ir.maktabsharif.final_project_taha_badri.exception.SuggestPriceIsLow;
import ir.maktabsharif.final_project_taha_badri.exception.UnverifiedExpertException;
import ir.maktabsharif.final_project_taha_badri.repository.proposal.ProposalRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.order.OrderService;
import ir.maktabsharif.final_project_taha_badri.service.user.expert.ExpertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
public class ProposalServiceImpl
        extends BaseServiceImpl<
        Proposal,
        Long,
        ProposalRepository,
        ProposalRequest,
        ProposalResponse,
        ProposalMapper>
        implements ProposalService {
    private final ExpertService expertService;
    private final OrderService orderService;

    public ProposalServiceImpl(
            ProposalRepository repository, ProposalMapper mapper
            , ExpertService expertService,
            OrderService orderService
    ) {
        super(repository, mapper);
        this.expertService = expertService;
        this.orderService = orderService;
    }

    protected void setEntityRelations(Proposal entity, ProposalRequest dto) {
        if (dto.orderId() != null) {
            entity.setOrder(orderService.findById(dto.orderId()));
        }

    }

    @Transactional
    @Override
    public ProposalResponse save(Long expertId, ProposalRequest dto) {

        Order order = orderService.findById(dto.orderId());
        Service service = order.getService();

        if (service == null) {
            throw new InvalidRequestException("Order service with orderId " + dto.orderId() + " is null.");
        }
        if (service.getBasePrice() > dto.suggestPrice()) {
            throw new SuggestPriceIsLow();
        }
        Proposal proposal = mapper.requestToEntity(dto);
        setEntityRelations(proposal, dto);
        proposal.setOrder(order);
        proposal.setExpert(expertService.findById(expertId));
        Expert expert = expertService.findById(expertId);
        if (!expert.getStatus().equals(ExpertStatus.ACCEPT)) {
            throw new UnverifiedExpertException();
        }
        Set<Service> services = new HashSet<>();
        services.add(service);
        boolean hasService = expertService.existsByServices(services);
        log.warn(order.toString());
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
            orderService.update(new OrderRequest(order.getId(), order.getOrderStatus()));
        }
        Long hours = createDuration(proposal.getStartWork(), proposal.getEndDate());
        proposal.setDuration(hours);
        proposal.setStatus(ProposalStatus.PENDING);
        return mapper.entityToResponse(repository.save(proposal));
    }


    @Override
    public ProposalResponse update(ProposalRequest dto) {
        if (dto.startWork() != null || dto.endDate() != null) {
            Long duration = createDuration(dto.startWork(), dto.startWork());
            Proposal proposal = mapper.requestToEntity(dto);
            mapper.updateEntityWithRequest(dto, proposal);
            proposal.setDuration(duration);
            return mapper.entityToResponse(repository.save(proposal));
        }
        Proposal byId = findById(dto.id());
        mapper.updateEntityWithRequest(dto, byId);
        return mapper.entityToResponse(repository.save(byId));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProposalResponse> findByOrderIdOrderBySuggestPrice(Long customerId, Long orderId, Pageable pageable) {
        extracted(customerId, orderId);
        Page<Proposal> proposals = repository.findByOrderIdOrderBySuggestPriceAsc(orderId, pageable);
        return proposals.map(mapper::entityToResponse);

    }

    @Transactional
    @Override
    public void setOrderWithProposal(Long proposalId) {
        repository.setOrderWithProposal(proposalId);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProposalResponse> findByOrderIdOrderByExpertScoreDesc(Long customerId, Long orderId, Pageable pageable) {
        extracted(customerId, orderId);
        return repository.findByOrderIdOrderByExpertScoreDesc(orderId, pageable)
                .map(mapper::entityToResponse);
    }

    private void extracted(Long customerId, Long orderId) {
        Order byId = orderService.findById(orderId);
        if (!Objects.equals(customerId, byId.getCustomer().getId())) {
            throw new IllegalArgumentException("Order with ID " + orderId +
                    " does not belong to customer with ID " + customerId);
        }
    }

    @Transactional
    @Override
    public void setOrderStatusToStartedByProposalId(Long customerId, Long proposalId) {
        Long orderId = findById(proposalId).getOrder().getId();
        extracted(customerId, orderId);
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
    public void chooseProposal(Long customerId, Long proposalId, Long orderId) {
        extracted(customerId, proposalId);
        this.setOrderWithProposal(proposalId);
        this.changeAllProposalStatusByOrderId(orderId, ProposalStatus.REJECTED);
        this.changeStatusById(proposalId, ProposalStatus.ACCEPTED);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<OrderResponse> findAllOrdersByExpert_Id(Long expertId, Pageable pageable) {
        return repository.findAllOrdersByExpert_Id(expertId, pageable);
    }


    private Long createDuration(ZonedDateTime start, ZonedDateTime end) {
        Duration duration = Duration.between(start, end);
        return duration.toHours();
    }
}
