package ir.maktabsharif.final_project_taha_badri.service.user.expert;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateExpert;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface ExpertService extends BaseService<Expert,Long, SaveOrUpdateExpert> {
    void changeExpertStatus(Long expertId, ExpertStatus newStatus);
    List<Expert> findAllWaitingExpertStatus();

    void addService(Long expertId, Long serviceId);
    void removeService(Long expertId, Long serviceId);

    Expert register(SaveOrUpdateExpert expertDto);

    Expert update(SaveOrUpdateExpert expertDto);

    boolean existsByServices(Set<ir.maktabsharif.final_project_taha_badri.domain.entity.Service> services);


}
