package ir.maktabsharif.final_project_taha_badri.repository.base.user;


import io.swagger.v3.oas.annotations.servers.Servers;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseUser;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface BaseUserRepository<T extends BaseUser> extends CrudRepository<T, Long> {

    T save(T user);

    Optional<T> findByUsername(String username);

    Optional<T> findByUsernameAndPassword(String username, String password);
}
