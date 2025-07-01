package ir.maktabsharif.final_project_taha_badri.service.home_service;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateService;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.ServiceMapper;
import ir.maktabsharif.final_project_taha_badri.repository.service.ServiceRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ServiceServiceImpl
        extends BaseServiceImpl<
        ir.maktabsharif.final_project_taha_badri.domain.entity.Service,
        Long,
        ServiceRepository,
        ServiceMapper,
        SaveOrUpdateService
        >
        implements ServiceService {

    public ServiceServiceImpl(ServiceRepository repository,ServiceMapper mapper) {
        super(repository,mapper);
    }
}

