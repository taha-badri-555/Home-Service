package ir.maktabsharif.final_project_taha_badri.service.home_service;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateService;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.ServiceMapper;
import ir.maktabsharif.final_project_taha_badri.repository.service.ServiceRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Transactional
public class ServiceServiceImpl
        extends BaseServiceImpl<
        ir.maktabsharif.final_project_taha_badri.domain.entity.Service,
        Long,
        ServiceRepository,
        SaveOrUpdateService,
        ServiceMapper
        >
        implements ServiceService {

    public ServiceServiceImpl(ServiceRepository repository, ServiceMapper mapper) {
        super(repository,mapper);
    }

    @Override
    public List<ir.maktabsharif.final_project_taha_badri.domain.entity.Service>
    getAllParenService() {
        return repository.findByParentServiceIsNull();
    }

    @Override
    public List<ir.maktabsharif.final_project_taha_badri.domain.entity.Service>
    getAllChildServiceByParentId(Long parentId) {
        ir.maktabsharif.final_project_taha_badri.domain.entity.Service
                service = findById(parentId);
        return repository.findByParentService(service);
    }
}

