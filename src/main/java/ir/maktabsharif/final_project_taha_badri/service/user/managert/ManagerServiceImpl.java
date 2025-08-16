package ir.maktabsharif.final_project_taha_badri.service.user.managert;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.ManagerRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.ManagerResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Manager;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.ManagerMapper;
import ir.maktabsharif.final_project_taha_badri.repository.user.manager.ManagerRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ManagerServiceImpl
        extends BaseServiceImpl<
        Manager,
        Long,
        ManagerRepository,
        ManagerRequest,
        ManagerResponse,
        ManagerMapper>
        implements ManagerService {
    private final PasswordEncoder passwordEncoder;

    public ManagerServiceImpl(
            ManagerRepository repository, ManagerMapper mapper, PasswordEncoder passwordEncoder) {
        super(repository, mapper);

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ManagerResponse update(ManagerRequest managerDTO) {
        if (managerDTO.password() != null) {
            Manager byId = findById(managerDTO.id());
            mapper.updateEntityWithRequest(managerDTO, byId);
            byId.setPassword(passwordEncoder.encode(managerDTO.password()));
        }
        return super.update(managerDTO);
    }
}
