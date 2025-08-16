package ir.maktabsharif.final_project_taha_badri.repository.user.expert;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.repository.base.user.BaseUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ExpertRepository extends BaseUserRepository<Expert> {

   @Modifying
   @Query("""
           update Expert e set e.status = :status where e.id = :id
           """)
   void changeStatus(Long id, ExpertStatus status);

   @Query("SELECT e FROM Expert e WHERE e.status = 'WAITING'")
   Page<Expert> findAllWaitingExpertStatus(Pageable pageable);


   boolean existsByServices(Set<Service> services);
}
