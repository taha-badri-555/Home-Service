package ir.maktabsharif.final_project_taha_badri.service.base;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

record DummyRequest(Long id) implements Identifiable<Long> {
    @Override
    public Long getId() {
        return id;
    }
}
record DummyResponse(Long id) implements Identifiable<Long> {
    @Override
    public Long getId() {
        return id;
    }
}

class DummyEntity extends BaseEntity<Long> {
    private Long id;
    public DummyEntity(Long id) { this.id = id; }
    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }
}

interface DummyRepository extends org.springframework.data.jpa.repository.JpaRepository<DummyEntity, Long> {}
interface DummyMapper extends BaseMapper<DummyRequest, DummyResponse, DummyEntity, Long> {}

class DummyService extends BaseServiceImpl<DummyEntity, Long, DummyRepository, DummyRequest, DummyResponse, DummyMapper> {
    public DummyService(DummyRepository repository, DummyMapper mapper) {
        super(repository, mapper);
    }
}

public class BaseServiceImplTest {

    @Mock
    private DummyRepository repository;
    @Mock
    private DummyMapper mapper;
    @InjectMocks
    private DummyService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldReturnMappedResponse() {
        DummyRequest request = new DummyRequest(1L);
        DummyEntity entity = new DummyEntity(1L);
        DummyResponse response = new DummyResponse(1L);

        when(mapper.requestToEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.entityToResponse(entity)).thenReturn(response);

        DummyResponse result = service.save(request);

        assertEquals(response, result);
        verify(mapper).requestToEntity(request);
        verify(repository).save(entity);
        verify(mapper).entityToResponse(entity);
    }

    @Test
    void update_shouldUpdateEntityAndReturnResponse() {
        DummyRequest request = new DummyRequest(1L);
        DummyEntity entity = new DummyEntity(1L);
        DummyResponse response = new DummyResponse(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(mapper).updateEntityWithRequest(request, entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.entityToResponse(entity)).thenReturn(response);

        DummyResponse result = service.update(request);

        assertEquals(response, result);
    }

    @Test
    void saveAll_shouldReturnPageOfResponses() {
        DummyRequest req1 = new DummyRequest(1L);
        DummyEntity ent1 = new DummyEntity(1L);
        DummyResponse res1 = new DummyResponse(1L);

        List<DummyRequest> requests = List.of(req1);
        List<DummyEntity> entities = List.of(ent1);

        when(mapper.requestToEntity(req1)).thenReturn(ent1);
        when(repository.saveAll(entities)).thenReturn(entities);
        when(mapper.entityToResponse(ent1)).thenReturn(res1);

        Page<DummyResponse> page = service.saveAll(requests);

        assertEquals(1, page.getContent().size());
    }

    @Test
    void findById_shouldReturnEntity() {
        DummyEntity entity = new DummyEntity(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        DummyEntity result = service.findById(1L);
        assertEquals(entity, result);
    }

    @Test
    void findById_shouldThrowExceptionIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.findById(1L));
    }

    @Test
    void getResponseById_shouldReturnMappedResponse() {
        DummyEntity entity = new DummyEntity(1L);
        DummyResponse response = new DummyResponse(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.entityToResponse(entity)).thenReturn(response);
        DummyResponse result = service.getResponseById(1L);
        assertEquals(response, result);
    }

    @Test
    void findAll_shouldReturnListOfResponses() {
        DummyEntity entity = new DummyEntity(1L);
        DummyResponse response = new DummyResponse(1L);
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.entityToResponse(entity)).thenReturn(response);
        List<DummyResponse> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findAllPageable_shouldReturnPagedResponses() {
        DummyEntity entity = new DummyEntity(1L);
        DummyResponse response = new DummyResponse(1L);
        Page<DummyEntity> entityPage = new PageImpl<>(List.of(entity));
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable)).thenReturn(entityPage);
        when(mapper.entityToResponse(entity)).thenReturn(response);
        Page<DummyResponse> result = service.findAll(pageable);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void findAllById_shouldReturnListOfResponses() {
        DummyEntity entity = new DummyEntity(1L);
        DummyResponse response = new DummyResponse(1L);
        when(repository.findAllById(List.of(1L))).thenReturn(List.of(entity));
        when(mapper.entityToResponse(entity)).thenReturn(response);
        List<DummyResponse> result = service.findAllById(List.of(1L));
        assertEquals(1, result.size());
    }

    @Test
    void countAll_shouldReturnCount() {
        when(repository.count()).thenReturn(5L);
        assertEquals(5L, service.countAll());
    }

    @Test
    void deleteById_shouldCallRepository() {
        doNothing().when(repository).deleteById(1L);
        service.deleteById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteAllById_shouldCallRepository() {
        List<Long> ids = List.of(1L, 2L);
        doNothing().when(repository).deleteAllById(ids);
        service.deleteAllById(ids);
        verify(repository).deleteAllById(ids);
    }

    @Test
    void existsById_shouldReturnTrueIfExists() {
        when(repository.existsById(1L)).thenReturn(true);
        assertTrue(service.existsById(1L));
    }

}
