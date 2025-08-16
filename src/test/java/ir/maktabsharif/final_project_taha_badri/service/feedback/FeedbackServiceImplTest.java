package ir.maktabsharif.final_project_taha_badri.service.feedback;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.FeedbackRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.FeedbackResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.FeedbackMapper;
import ir.maktabsharif.final_project_taha_badri.repository.feedback.FeedbackRepository;
import ir.maktabsharif.final_project_taha_badri.service.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository repository;

    @Mock
    private FeedbackMapper mapper;

    @Mock
    private OrderService orderService;

    @Spy
    @InjectMocks
    private FeedbackServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void getScoresAVGByExpertId_returnsValueFromRepository() {
        when(repository.getScoresAVGByExpertId(5L)).thenReturn(4.7);
        assertEquals(4.7, service.getScoresAVGByExpertId(5L));
    }

    @Test
    void getScoreByOrderAndExpertId_returnsValueFromRepository() {
        when(repository.getScoreByOrderAndExpertId(3L, 4L)).thenReturn((byte) 4);
        assertEquals((byte) 4, service.getScoreByOrderAndExpertId(3L, 4L));
    }

    @Test
    void setEntityRelations_setsOrderWhenOrderIdNotNull() {
        Feedback feedback = new Feedback();
        FeedbackRequest dto = mock(FeedbackRequest.class);
        when(dto.orderId()).thenReturn(7L);

        Order order = new Order();
        when(orderService.findById(7L)).thenReturn(order);

        service.setEntityRelations(feedback, dto);

        assertEquals(order, feedback.getOrder());
    }

    @Test
    void findByOrderIdAndExpertId_returnsFromRepository() {
        Feedback feedback = new Feedback();
        when(repository.findByOrderIdAndExpertId(1L, 2L)).thenReturn(feedback);

        Feedback result = service.findByOrderIdAndExpertId(1L, 2L);
        assertEquals(feedback, result);
    }

    @Test
    void getAllScoreByExpertId_returnsFromRepository() {
        when(repository.getAllScoreByExpertId(3L)).thenReturn(15L);
        assertEquals(15L, service.getAllScoreByExpertId(3L));
    }

    @Test
    void findByOrderId_whenFound_returnsFeedback() {
        Feedback feedback = new Feedback();
        when(repository.findByOrder_Id(1L)).thenReturn(Optional.of(feedback));

        Feedback result = service.findByOrderId(1L);
        assertEquals(feedback, result);
    }

    @Test
    void findByOrderId_whenNotFound_throwsException() {
        when(repository.findByOrder_Id(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findByOrderId(1L));
    }
}
