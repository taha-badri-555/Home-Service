package ir.maktabsharif.final_project_taha_badri.service.user.expert;

import ir.maktabsharif.final_project_taha_badri.domain.dto.user.SaveOrUpdateExpert;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.ExpertMapper;
import ir.maktabsharif.final_project_taha_badri.repository.user.expert.ExpertRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.home_service.ServiceService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Primary

public class ExpertServiceImpl
        extends BaseServiceImpl<
        Expert,
        Long,
        ExpertRepository,
        ExpertMapper,
        SaveOrUpdateExpert>
        implements ExpertService {
    private final ServiceService serviceService;

    public ExpertServiceImpl(
            ExpertRepository repository,
            ExpertMapper mapper,
            ServiceService serviceService) {
        super(repository, mapper);
        this.serviceService = serviceService;
    }

    @Override
    public void acceptExpertStatus(Long id) {
        repository.beginTransaction();
        repository.acceptExpertStatus(id);
        repository.commitTransaction();
    }

    @Override
    public void newExpertStatus(Long id) {
        repository.beginTransaction();
        repository.newExpertStatus(id);
        repository.commitTransaction();


    }

    @Override
    public void waitingExpertStatus(Long id) {
        repository.beginTransaction();
        repository.waitingExpertStatus(id);
        repository.commitTransaction();


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
        repository.beginTransaction();
        repository.saveAndUpdate(expert);
        repository.commitTransaction();

    }

    @Override
    public void removeService(Long expertId, Long serviceId) {
        Expert expert = findById(expertId);
        ir.maktabsharif.final_project_taha_badri.domain.entity.Service
                service = serviceService.findById(serviceId);
        expert.getServices().remove(service);
        repository.beginTransaction();
        repository.saveAndUpdate(expert);
        repository.commitTransaction();
    }
}
