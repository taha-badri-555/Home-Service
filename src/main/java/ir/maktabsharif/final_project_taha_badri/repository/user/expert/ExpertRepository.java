package ir.maktabsharif.final_project_taha_badri.repository.user.expert;

import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.repository.base.user.BaseUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepository extends BaseUserRepository<Expert> {
   void acceptExpertStatus(Long id);
   void newExpertStatus(Long id);
   void waitingExpertStatus(Long id);
   List<Expert> findAllWaitingExpertStatus();
}
