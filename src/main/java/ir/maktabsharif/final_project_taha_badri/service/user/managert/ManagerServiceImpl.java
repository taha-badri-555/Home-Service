package ir.maktabsharif.final_project_taha_badri.service.user.managert;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateManager;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Manager;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.ManagerMapper;
import ir.maktabsharif.final_project_taha_badri.repository.user.manager.ManagerRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ManagerServiceImpl
        extends BaseServiceImpl<
        Manager,
        Long,
        ManagerRepository,
        ManagerMapper,
        SaveOrUpdateManager>
        implements ManagerService {

    public ManagerServiceImpl(ManagerRepository repository, ManagerMapper mapper) {
        super(repository, mapper);

    }

}
