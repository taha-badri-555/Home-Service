package ir.maktabsharif.final_project_taha_badri.service.user.expert;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateWallet;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.EmailRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateExpert;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.ExpertMapper;
import ir.maktabsharif.final_project_taha_badri.exception.ExpertUpdateBlockedDueToActiveOrdersException;
import ir.maktabsharif.final_project_taha_badri.exception.ImageLengthOutOfBoundException;
import ir.maktabsharif.final_project_taha_badri.exception.UserWithSameEmailExistsException;
import ir.maktabsharif.final_project_taha_badri.repository.user.expert.ExpertRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.home_service.ServiceService;
import ir.maktabsharif.final_project_taha_badri.service.order.OrderService;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import ir.maktabsharif.final_project_taha_badri.service.wallet.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ExpertServiceImpl
        extends BaseServiceImpl<
        Expert,
        Long,
        ExpertRepository,
        SaveOrUpdateExpert
        , ExpertMapper>
        implements ExpertService {
    private final UserService userService;
    private final ServiceService serviceService;
    private final WalletService walletService;
    private final OrderService orderService;

    public ExpertServiceImpl(
            ExpertRepository repository,
            ExpertMapper mapper, UserService userService,
            ServiceService serviceService, WalletService walletService,
            @Lazy OrderService orderService) {
        super(repository, mapper);
        this.userService = userService;
        this.serviceService = serviceService;
        this.walletService = walletService;
        this.orderService = orderService;
    }

    @Override
    public void changeExpertStatus(Long expertId, ExpertStatus newStatus) {
        repository.changeStatus(expertId, newStatus);
    }

    @Override
    public List<Expert> findAllWaitingExpertStatus() {
        return repository.findAllWaitingExpertStatus();
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

    @Override
    public Expert register(SaveOrUpdateExpert expertDto) {
        Expert expert = mapper.dtoToEntity(expertDto);
        if (expert.getImagePath() != null) {
            byte[] image = getBytesForExpert(expert.getImagePath());
            if (image.length > 300_000) {
                throw new ImageLengthOutOfBoundException();
            }
            expert.setStatus(ExpertStatus.WAITING);
        } else {
            expert.setStatus(ExpertStatus.NEW);
        }
        if (userService.existsByEmail(new EmailRequest(expertDto.email()))) {
            throw new UserWithSameEmailExistsException();
        }

        expert.setEmail(expertDto.email());
        walletService.save(new SaveOrUpdateWallet(null, 0D, expert.getId()));
        return repository.save(expert);
    }

    @Override
    public Expert update(SaveOrUpdateExpert dto) {
        Expert expert = findById(dto.id());
        List<OrderStatus> statuses =
                List.of(OrderStatus.WAITING_FOR_EXPERT_TO_VISIT, OrderStatus.STARTED);
        boolean busy = orderService.existsByExpertIdtAndOrderStatusIn(expert.getId(), statuses);
        if (busy) {
            throw new ExpertUpdateBlockedDueToActiveOrdersException(
                    "Expert " + expert.getEmail() + " has " + " active order(s)."
            );
        }
        byte[] image = getBytesForExpert(expert.getImagePath());
        if (image.length > 300_000) {
            throw new ImageLengthOutOfBoundException();
        }
        mapper.updateEntityWithDTO(dto, expert);
        expert.setStatus(ExpertStatus.WAITING);
        return repository.save(expert);
    }

    @Override
    public boolean existsByServices(Set<ir.maktabsharif.final_project_taha_badri.domain.entity.Service> services) {
        return repository.existsByServices(services);
    }

    private byte[] getBytesForExpert(String imagePath) {
        String normalizedPath = imagePath.replace("\\", File.separator);
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(Paths.get(normalizedPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageBytes;
    }
}
