package ir.maktabsharif.final_project_taha_badri.repository.service;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository
        extends JpaRepository<Service, Long>, JpaSpecificationExecutor<Service> {

    List<Service> findByParentServiceIsNull();

    List<Service> findByParentService(Service service);
}
