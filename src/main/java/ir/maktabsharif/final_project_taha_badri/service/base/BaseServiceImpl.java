package ir.maktabsharif.final_project_taha_badri.service.base;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BaseServiceImpl<
        T extends BaseEntity<ID>,
        ID,
        R extends CrudRepository<T, ID>,
        M extends BaseMapper<DTO, T>
        , DTO>
        implements BaseService<T, ID, DTO> {

    protected final R repository;
    protected final M mapper;

    @Override
    public T save(DTO dto) {
        T entity = mapper.dtoToEntity(dto);
        repository.beginTransaction();
        repository.save(entity);
        repository.commitTransaction();
        return entity;
    }

    @Override
    public T saveOrUpdate(DTO dto) {
        T entity = mapper.dtoToEntity(dto);
        repository.beginTransaction();
        entity = repository.saveAndUpdate(entity);
        repository.commitTransaction();
        return entity;
    }

    @Override
    public List<T> saveAll(Collection<DTO> dtos) {
        List<T> entities = dtos.stream()
                .map(mapper::dtoToEntity)
                .collect(Collectors.toList());

        repository.beginTransaction();
        List<T> savedEntities = repository.saveAll(entities);
        repository.commitTransaction();

        return savedEntities;
    }


    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Entity not found with id: " + id));
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public long countAll() {
        return repository.countAll();
    }

    @Override
    public void deleteById(ID id) {
        repository.beginTransaction();
        repository.deleteById(id);
        repository.commitTransaction();
    }

    @Override
    public void deleteAllById(Iterable<ID> ids) {
        repository.beginTransaction();
        repository.deleteAllById(ids);
        repository.commitTransaction();
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }
}

