package ir.maktabsharif.final_project_taha_badri.service.home_service;


import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateService;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ServiceService
        extends BaseService
        <ir.maktabsharif.final_project_taha_badri.domain.entity.Service, Long, SaveOrUpdateService> {

    List<ir.maktabsharif.final_project_taha_badri.domain.entity.Service>
    getAllParenService();

    List<ir.maktabsharif.final_project_taha_badri.domain.entity.Service>
    getAllChildServiceByParentId(Long parentId);
}
