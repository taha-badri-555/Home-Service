package ir.maktabsharif.final_project_taha_badri.service.base;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@SuperBuilder
public class BaseServiceImpl<
        T extends BaseEntity<ID>,
        ID,
        R extends JpaRepository<T, ID>,
        REQUEST extends Identifiable<ID>,
        RESPONSE extends Identifiable<ID>,
        M extends BaseMapper<REQUEST, RESPONSE, T, ID>>
        implements BaseService<T, ID, REQUEST, RESPONSE> {
    protected final R repository;
    protected final M mapper;

    @Override
    public RESPONSE save(REQUEST dto) {
        T entity = mapper.requestToEntity(dto);
        setEntityRelations(entity, dto);
        repository.save(entity);
        return mapper.entityToResponse(entity);
    }

    @Override
    public RESPONSE update(REQUEST dto) {
        ID id = dto.getId();
        T entity = findById(id);

        mapper.updateEntityWithRequest(dto, entity);
        setEntityRelations(entity, dto);

        repository.save(entity);

        return mapper.entityToResponse(entity);
    }


    protected void setEntityRelations(T entity, REQUEST dto) {
    }

    @Override
    public Page<RESPONSE> saveAll(Collection<REQUEST> dtos) {
        List<T> entities = dtos.stream()
                .map(dto -> {
                    T entity = mapper.requestToEntity(dto);
                    setEntityRelations(entity, dto);
                    return entity;
                })
                .collect(Collectors.toList());

        List<T> savedEntities = repository.saveAll(entities);

        List<RESPONSE> dtoList = savedEntities.stream()
                .map(mapper::entityToResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList);
    }


    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(getClass() + " not found with id: " + id));

    }

    @Override

    public RESPONSE getResponseById(ID id) {
        return mapper.entityToResponse(repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(getClass() + " not found with id: " + id)));
    }

    @Override
    public List<RESPONSE> findAll() {
        List<T> all = repository.findAll();
        return all.stream()
                .map(mapper::entityToResponse).toList();
    }

    @Override
    public Page<RESPONSE> findAll(Pageable pageable) {
        Page<T> entityPage = repository.findAll(pageable);
        return entityPage.map(mapper::entityToResponse);
    }


    @Override
    public List<RESPONSE> findAllById(Iterable<ID> ids) {
        List<T> allById = repository.findAllById(ids);
        List<RESPONSE> dtos = allById.stream()
                .map(mapper::entityToResponse)
                .collect(Collectors.toList());
        return dtos;
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

    private RESPONSE apply(T entity) {
        RESPONSE dto = mapper.entityToResponse(entity);
        return dto;
    }


}

