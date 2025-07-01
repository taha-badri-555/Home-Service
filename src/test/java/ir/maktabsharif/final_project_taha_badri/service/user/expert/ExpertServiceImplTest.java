package ir.maktabsharif.final_project_taha_badri.service.user.expert;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.ExpertMapper;
import ir.maktabsharif.final_project_taha_badri.repository.user.expert.ExpertRepository;
import ir.maktabsharif.final_project_taha_badri.service.home_service.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ExpertServiceImplTest {

    private ExpertRepository expertRepository;
    private ExpertMapper expertMapper;
    private ServiceService serviceService;
    private ExpertServiceImpl expertService;

    @BeforeEach
    void setUp() {
        expertRepository = mock(ExpertRepository.class);
        expertMapper = mock(ExpertMapper.class);
        serviceService = mock(ServiceService.class);
        expertService = new ExpertServiceImpl(expertRepository, expertMapper, serviceService);
    }

    @Test
    void addService_shouldAddServiceToExpertAndSave() {
        Long expertId = 1L;
        Long serviceId = 2L;

        Expert expert = new Expert();
        expert.setServices(new ArrayList<>());

        Service service = mock(Service.class);

        when(expertRepository.findById(expertId)).thenReturn(Optional.of(expert));
        when(serviceService.findById(serviceId)).thenReturn(service);

        expertService.addService(expertId, serviceId);

        verify(expertRepository).beginTransaction();
        verify(expertRepository).saveAndUpdate(expert);
        verify(expertRepository).commitTransaction();

        assert expert.getServices().contains(service);
    }

    @Test
    void removeService_shouldRemoveServiceFromExpertAndSave() {
        Long expertId = 1L;
        Long serviceId = 2L;

        Service service = mock(Service.class);
        Expert expert = new Expert();
        expert.setServices(new ArrayList<>(Collections.singleton(service)));

        when(expertRepository.findById(expertId)).thenReturn(Optional.of(expert));
        when(serviceService.findById(serviceId)).thenReturn(service);

        expertService.removeService(expertId, serviceId);

        verify(expertRepository).beginTransaction();
        verify(expertRepository).saveAndUpdate(expert);
        verify(expertRepository).commitTransaction();

        assert !expert.getServices().contains(service);
    }

    @Test
    void addService_shouldThrowException_whenExpertNotFound() {
        when(expertRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                expertService.addService(1L, 2L));
    }

    @Test
    void removeService_shouldThrowException_whenExpertNotFound() {
        when(expertRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                expertService.removeService(1L, 2L));
    }
}
