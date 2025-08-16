package ir.maktabsharif.final_project_taha_badri.service.home_service;


import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ServiceRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.ServiceResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ServiceService
        extends BaseService
        <ir.maktabsharif.final_project_taha_badri.domain.entity.Service, Long, ServiceRequest, ServiceResponse> {

    Page<ServiceResponse> getAllParentServices(Pageable pageable) ;



    Page<ServiceResponse>
    getAllChildServiceByParentId(Long parentId,Pageable pageable);
}
