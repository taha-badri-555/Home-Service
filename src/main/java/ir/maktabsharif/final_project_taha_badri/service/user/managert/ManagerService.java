package ir.maktabsharif.final_project_taha_badri.service.user.managert;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateManager;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Manager;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface ManagerService extends BaseService<Manager, Long, SaveOrUpdateManager> {

}
