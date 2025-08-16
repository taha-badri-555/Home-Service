package ir.maktabsharif.final_project_taha_badri.service.user.expert;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ChangeImagePatch;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.user.ExpertRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.user.ExpertResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ExpertService extends BaseService<Expert, Long, ExpertRequest,ExpertResponse> {

    ExpertResponse update(Long id, ExpertRequest dto);

    void changeExpertStatus(Long expertId, ExpertStatus newStatus);

    Page<ExpertResponse> findAllWaitingExpertStatus(int page, int size);

    void addService(Long expertId, Long serviceId);

    void removeService(Long expertId, Long serviceId);

    ExpertResponse register(ExpertRequest expertRequest);

    ExpertResponse update(ExpertRequest expertRequest);

    boolean existsByServices(Set<ir.maktabsharif.final_project_taha_badri.domain.entity.Service> services);

    ExpertResponse changeImage(Long expertId, ChangeImagePatch dto);

}
