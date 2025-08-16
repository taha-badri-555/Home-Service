package ir.maktabsharif.final_project_taha_badri.service.home_service;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ServiceRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.ServiceResponse;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.ServiceMapper;
import ir.maktabsharif.final_project_taha_badri.repository.service.ServiceRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Primary
@Transactional
public class ServiceServiceImpl
        extends BaseServiceImpl<
        ir.maktabsharif.final_project_taha_badri.domain.entity.Service,
        Long,
        ServiceRepository,
        ServiceRequest,
        ServiceResponse,
        ServiceMapper
        >
        implements ServiceService {

    public ServiceServiceImpl(ServiceRepository repository, ServiceMapper mapper) {
        super(repository,mapper);
    }

    @Override
    public Page<ServiceResponse> getAllParentServices(Pageable pageable) {
        return repository.findByParentServiceIsNull(pageable)
                .map(mapper::entityToResponse);
    }

    @Override
    public Page<ServiceResponse>
    getAllChildServiceByParentId(Long parentId, Pageable pageable) {
        return repository.findByParentService(parentId, pageable)
                .map(mapper::entityToResponse);
    }

    @Override
    protected void setEntityRelations(ir.maktabsharif.final_project_taha_badri.domain.entity.Service entity, ServiceRequest dto) {
        if (dto.serviceId() != null) {
            ir.maktabsharif.final_project_taha_badri.domain.entity.Service
                    byId = findById(dto.serviceId());
            entity.setParentService(byId);
        }
    }
}

