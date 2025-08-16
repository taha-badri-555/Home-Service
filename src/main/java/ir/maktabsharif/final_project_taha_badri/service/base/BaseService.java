package ir.maktabsharif.final_project_taha_badri.service.base;


import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface BaseService<T extends BaseEntity<ID>, ID, REQUEST,RESPONSE> {

    RESPONSE save(REQUEST dto);

    RESPONSE update(REQUEST dto);

    Page<RESPONSE> saveAll(Collection<REQUEST> entities);

    T findById(ID id);

    RESPONSE getResponseById(ID id);

    List<RESPONSE> findAll();

    List<RESPONSE> findAllById(Iterable<ID> ids);

    long countAll();

    void deleteById(ID id);

    void deleteAllById(Iterable<ID> ids);

    boolean existsById(ID id);

    Page<RESPONSE> findAll(Pageable pageable);
}
