package ir.maktabsharif.final_project_taha_badri.repository.base.user;


import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseUserRepository<T extends Person>
        extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    Optional<T> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmailAndId(String email, Long id);

    @Query("""
    select u
    from Person u
    where u.email = :email
    """)
    Optional<Person> findByEmail(@Param("email") String email);

}
