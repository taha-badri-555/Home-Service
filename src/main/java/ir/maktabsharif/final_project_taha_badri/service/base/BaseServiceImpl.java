package ir.maktabsharif.final_project_taha_badri.service.base;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BaseServiceImpl<
        T extends BaseEntity<ID>,
        ID,
        R extends JpaRepository<T, ID>
        , DTO extends Identifiable<ID>
        , M extends BaseMapper<DTO, T, ID>>
        implements BaseService<T, ID, DTO> {
    protected final R repository;
    protected final M mapper;

    @Override
    public T save(DTO dto) {
        T entity = mapper.dtoToEntity(dto);
        setEntityRelations(entity, dto);
        repository.save(entity);
        return entity;
    }

    @Override
    public T update(DTO dto) {
        ID id = dto.getId();
        T entity = findById(id);
        mapper.updateEntityWithDTO(dto, entity);
        setEntityRelations(entity, dto);
        return repository.save(entity);

    }

    protected void setEntityRelations(T entity, DTO dto) {
    }

    @Override
    public List<T> saveAll(Collection<DTO> dtos) {
        List<T> entities = dtos.stream()
                .map(mapper::dtoToEntity)
                .collect(Collectors.toList());

        return repository.saveAll(entities);
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
    public List<T> findAll(int page, int size) {
        Page<T> all = repository.findAll(PageRequest.of(page, page));
        return all.getContent();
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public long countAll() {
        return repository.count();
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllById(Iterable<ID> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }
}

