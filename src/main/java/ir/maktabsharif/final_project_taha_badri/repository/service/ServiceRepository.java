package ir.maktabsharif.final_project_taha_badri.repository.service;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository
        extends JpaRepository<Service, Long>, JpaSpecificationExecutor<Service> {
    @Query("""
                select s from Service s where s.parentService is null
            """)
    Page<Service> findByParentServiceIsNull(Pageable pageable);

    @Query("""
                select s from Service s where s.parentService.id = :parentId
            """)
    Page<Service> findByParentService(@Param("parentId") Long parentId, Pageable pageable);

}