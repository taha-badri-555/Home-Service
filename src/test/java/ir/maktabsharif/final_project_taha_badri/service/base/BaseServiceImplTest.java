package ir.maktabsharif.final_project_taha_badri.service.base;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseServiceImplTest {

    CrudRepository<BaseEntity<Long>, Long> repository;
    BaseMapper<Object, BaseEntity<Long>> mapper;
    BaseServiceImpl<
            BaseEntity<Long>,
            Long,
            CrudRepository<BaseEntity<Long>, Long>,
            BaseMapper<Object, BaseEntity<Long>>, Object> service;

    @BeforeEach
    void setUp() {
        repository = mock(CrudRepository.class);
        mapper = mock(BaseMapper.class);
        service = new BaseServiceImpl<>(repository, mapper);
    }

    @Test
    void testSave() {
        Object dto = new Object();
        BaseEntity<Long> entity = mock(BaseEntity.class);

        when(mapper.dtoToEntity(dto)).thenReturn(entity);

        BaseEntity<Long> result = service.save(dto);

        verify(repository).beginTransaction();
        verify(repository).save(entity);
        verify(repository).commitTransaction();
        assertEquals(entity, result);
    }

    @Test
    void testSaveOrUpdate() {
        Object dto = new Object();
        BaseEntity<Long> entity = mock(BaseEntity.class);

        when(mapper.dtoToEntity(dto)).thenReturn(entity);
        when(repository.saveAndUpdate(entity)).thenReturn(entity);

        BaseEntity<Long> result = service.saveOrUpdate(dto);

        verify(repository).beginTransaction();
        verify(repository).saveAndUpdate(entity);
        verify(repository).commitTransaction();
        assertEquals(entity, result);
    }

    @Test
    void testFindById_found() {
        BaseEntity<Long> entity = mock(BaseEntity.class);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        BaseEntity<Long> result = service.findById(1L);
        assertEquals(entity, result);
    }

    @Test
    void testFindById_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.findById(1L));
    }

    @Test
    void testDeleteById() {
        service.deleteById(1L);
        verify(repository).beginTransaction();
        verify(repository).deleteById(1L);
        verify(repository).commitTransaction();
    }

    @Test
    void testExistsById() {
        when(repository.existsById(1L)).thenReturn(true);
        assertTrue(service.existsById(1L));
    }

    @Test
    void testFindAll() {
        List<BaseEntity<Long>> list = List.of(mock(BaseEntity.class));
        when(repository.findAll()).thenReturn(list);

        List<BaseEntity<Long>> result = service.findAll();
        assertEquals(list, result);
    }

    @Test
    void testCountAll() {
        when(repository.countAll()).thenReturn(10L);
        assertEquals(10L, service.countAll());
    }

    @Test
    void testSaveAll() {
        Object dto1 = new Object();
        Object dto2 = new Object();
        BaseEntity<Long> e1 = mock(BaseEntity.class);
        BaseEntity<Long> e2 = mock(BaseEntity.class);

        when(mapper.dtoToEntity(dto1)).thenReturn(e1);
        when(mapper.dtoToEntity(dto2)).thenReturn(e2);
        when(repository.saveAll(List.of(e1, e2))).thenReturn(List.of(e1, e2));

        List<BaseEntity<Long>> result = service.saveAll(List.of(dto1, dto2));

        verify(repository).beginTransaction();
        verify(repository).saveAll(List.of(e1, e2));
        verify(repository).commitTransaction();

        assertEquals(2, result.size());
    }

    @Test
    void testFindAllById() {
        List<Long> ids = List.of(1L, 2L);
        List<BaseEntity<Long>> entities = List.of(mock(BaseEntity.class), mock(BaseEntity.class));

        when(repository.findAllById(ids)).thenReturn(entities);

        List<BaseEntity<Long>> result = service.findAllById(ids);

        assertEquals(entities, result);
    }

    @Test
    void testDeleteAllById() {
        List<Long> ids = List.of(1L, 2L);

        service.deleteAllById(ids);

        verify(repository).beginTransaction();
        verify(repository).deleteAllById(ids);
        verify(repository).commitTransaction();
    }
}
