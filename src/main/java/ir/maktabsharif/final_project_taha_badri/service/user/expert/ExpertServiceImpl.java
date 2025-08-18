package ir.maktabsharif.final_project_taha_badri.service.user.expert;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ChangeImagePatch;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.EmailRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.WalletRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.ExpertRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.ExpertResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.Role;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.ExpertMapper;
import ir.maktabsharif.final_project_taha_badri.exception.ExpertUpdateBlockedDueToActiveOrdersException;
import ir.maktabsharif.final_project_taha_badri.exception.ImageLengthOutOfBoundException;
import ir.maktabsharif.final_project_taha_badri.exception.UserWithSameEmailExistsException;
import ir.maktabsharif.final_project_taha_badri.repository.user.expert.ExpertRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.email.EmailService;
import ir.maktabsharif.final_project_taha_badri.service.home_service.ServiceService;
import ir.maktabsharif.final_project_taha_badri.service.order.OrderService;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import ir.maktabsharif.final_project_taha_badri.service.wallet.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Service
@Transactional
public class ExpertServiceImpl
        extends BaseServiceImpl<
        Expert,
        Long,
        ExpertRepository,
        ExpertRequest,
        ExpertResponse,
        ExpertMapper>
        implements ExpertService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ServiceService serviceService;
    private final WalletService walletService;
    private final OrderService orderService;
    private final EmailService emailService;

    public ExpertServiceImpl(
            ExpertRepository repository,
            ExpertMapper mapper, PasswordEncoder passwordEncoder, UserService userService,
            ServiceService serviceService, WalletService walletService,
            @Lazy OrderService orderService, @Lazy EmailService emailService) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.serviceService = serviceService;
        this.walletService = walletService;
        this.orderService = orderService;
        this.emailService = emailService;
    }

    @Override
    public void changeExpertStatus(Long expertId, ExpertStatus newStatus) {
        repository.changeStatus(expertId, newStatus);
    }

    @Override
    public Page<ExpertResponse> findAllWaitingExpertStatus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Expert> expertPage = repository.findAllWaitingExpertStatus(pageable);
        return expertPage.map(mapper::entityToResponse);
    }


    @Override
    public void addService(Long expertId, Long serviceId) {
        Expert expert = findById(expertId);
        ir.maktabsharif.final_project_taha_badri.domain.entity.Service
                service = serviceService.findById(serviceId);
        expert.getServices().add(service);
        repository.save(expert);

    }

    @Override
    public void removeService(Long expertId, Long serviceId) {
        Expert expert = findById(expertId);
        ir.maktabsharif.final_project_taha_badri.domain.entity.Service
                service = serviceService.findById(serviceId);
        expert.getServices().remove(service);

        repository.save(expert);

    }

    @Transactional
    @Override
    public ExpertResponse register(ExpertRequest expertRequest) {
        Expert expert = mapper.requestToEntity(expertRequest);
        expert.setRole(Role.EXPERT);
        if (expertRequest.image() != null) {
            byte[] image = expertRequest.image();
            if (image.length > 300_000) {
                throw new ImageLengthOutOfBoundException();
            }
            expert.setImage(expertRequest.image());

        }
        expert.setStatus(ExpertStatus.NEW);

        if (userService.existsByEmail(new EmailRequest(expertRequest.email()))) {
            throw new UserWithSameEmailExistsException();
        }

        expert.setEmail(expertRequest.email());
        expert.setAvgScore(0D);
        expert.setPassword(passwordEncoder.encode(expertRequest.password()));
        expert.setImage(expertRequest.image());
        expert.setScore(0D);
        Expert save = repository.save(expert);

        emailService.createAndSendVerificationMail(expert);
        walletService.save(new WalletRequest(null, 0D, save.getId()));

        return mapper.entityToResponse(save);

    }

    @Override
    public boolean existsByServices(Set<Service> services) {
        return repository.existsByServices(services);
    }

    @Transactional
    @Override
    public ExpertResponse update(Long expertId, ExpertRequest dto) {
        byte[] path = dto.image();
        Expert expert = findById(expertId);
        List<OrderStatus> statuses =
                List.of(OrderStatus.WAITING_FOR_EXPERT_TO_VISIT, OrderStatus.STARTED);
        boolean busy = orderService.existsByExpertIdtAndOrderStatusIn(expert.getId(), statuses);
        if (dto.avgScore() != null || dto.status() != null
                && dto.image() == null
                && dto.email() == null
                && dto.password() == null) {
            if (dto.avgScore() != null) {
                expert.setAvgScore(dto.avgScore());
                repository.save(expert);
            }
            if (dto.status() != null) {
                expert.setStatus(dto.status());
                repository.save(expert);
            } else {

                if (busy) {
                    throw new ExpertUpdateBlockedDueToActiveOrdersException(
                            "Expert " + expert.getEmail() + " has " + " active order(s)."
                    );
                }
                expert.setStatus(ExpertStatus.WAITING);
            }
        }

        mapper.updateEntityWithRequest(dto, expert);
        expert.setImage(path);
        if (dto.password() != null) {
            expert.setPassword(passwordEncoder.encode(dto.password()));
        }
        if (dto.email() != null && !dto.email().equals(expert.getEmail())) {
            expert.setEmail(dto.email());
            expert.setVerified(false);
            emailService.createAndSendVerificationMail(expert);
        }
        Expert save = repository.save(expert);
        return mapper.entityToResponse(save);
    }

    @Transactional
    @Override
    public ExpertResponse changeImage(Long expertId, ChangeImagePatch dto) {
        Expert expert = findById(expertId);
        List<OrderStatus> statuses = List.of(OrderStatus.WAITING_FOR_EXPERT_TO_VISIT, OrderStatus.STARTED);
        boolean busy = orderService.existsByExpertIdtAndOrderStatusIn(expert.getId(), statuses);
        if (busy) {
            throw new ExpertUpdateBlockedDueToActiveOrdersException(
                    "Expert " + expert.getEmail() + " has active order(s)."
            );
        }
        if (expert.getImage() != null && expert.isVerified() == true) {
            expert.setStatus(ExpertStatus.WAITING);
        }

        expert.setImage(dto.image());

        return mapper.entityToResponse(repository.save(expert));
    }


}
