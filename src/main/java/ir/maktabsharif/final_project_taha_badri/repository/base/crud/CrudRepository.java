package ir.maktabsharif.final_project_taha_badri.repository.base.crud;


import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Repository
public interface CrudRepository<T extends BaseEntity<ID>, ID> {

    T save(T entity);

    T saveAndUpdate(T entity);

    List<T> saveAll(Collection<T> entities);

    Optional<T> findById(ID id);

    List<T> findAll();

    List<T> findAllById(Iterable<ID> ids);

    long countAll();

    void deleteById(ID id);

    void deleteAllById(Iterable<ID> ids);

    boolean existsById(ID id);

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();

}
