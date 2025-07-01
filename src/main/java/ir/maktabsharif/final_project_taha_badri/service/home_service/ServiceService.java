package ir.maktabsharif.final_project_taha_badri.service.home_service;


import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateService;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;


@Service
public interface ServiceService
        extends BaseService
        <ir.maktabsharif.final_project_taha_badri.domain.entity.Service, Long, SaveOrUpdateService> {
}
