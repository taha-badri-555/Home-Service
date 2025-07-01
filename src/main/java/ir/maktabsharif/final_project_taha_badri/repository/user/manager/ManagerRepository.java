package ir.maktabsharif.final_project_taha_badri.repository.user.manager;

import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Manager;
import ir.maktabsharif.final_project_taha_badri.repository.base.user.BaseUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends BaseUserRepository<Manager> {
}
