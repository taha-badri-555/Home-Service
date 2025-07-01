package ir.maktabsharif.final_project_taha_badri.service.user.expert;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateExpert;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpertService extends BaseService<Expert,Long, SaveOrUpdateExpert> {
    void acceptExpertStatus(Long id);
    void newExpertStatus(Long id);
    void waitingExpertStatus(Long id);
    List<Expert> findAllWaitingExpertStatus();

    void addService(Long expertId, Long serviceId);
    void removeService(Long expertId, Long serviceId);
}
