package ir.maktabsharif.final_project_taha_badri.repository.base.user;


import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface BaseUserRepository<T extends BaseUser>
        extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    Optional<T> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmailAndId(String email, Long id);
}
