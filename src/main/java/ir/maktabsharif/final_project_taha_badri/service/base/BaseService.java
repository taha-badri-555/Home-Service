package ir.maktabsharif.final_project_taha_badri.service.base;


import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;

import java.util.Collection;
import java.util.List;

public interface BaseService<T extends BaseEntity<ID>, ID, DTO> {

    T save(DTO dto);

    T update(DTO dto);

    List<T> saveAll(Collection<DTO> entities);

    T findById(ID id);

    List<T> findAll();

    List<T> findAllById(Iterable<ID> ids);

    long countAll();

    void deleteById(ID id);

    void deleteAllById(Iterable<ID> ids);

    boolean existsById(ID id);

    List<T> findAll(int page, int size);
}
