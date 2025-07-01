package ir.maktabsharif.final_project_taha_badri.service.order;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateOrder;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.OrderMapper;
import ir.maktabsharif.final_project_taha_badri.repository.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderMapper = mock(OrderMapper.class);
        orderService = new OrderServiceImpl(orderRepository, orderMapper);
    }

    @Test
    void saveOrUpdateProposal_shouldThrowException_whenProposedPriceIsLowerThanBasePrice() {
        SaveOrUpdateOrder dto = mock(SaveOrUpdateOrder.class);
        Order order = mock(Order.class);
        Service service = mock(Service.class);

        when(orderMapper.dtoToEntity(dto)).thenReturn(order);
        when(order.getProposedPrice()).thenReturn((int) 80_000D);
        when(order.getService()).thenReturn(service);
        when(service.getBasePrice()).thenReturn((int) 100_000D);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.saveOrUpdateProposal(dto));

        assertEquals("Proposed price is lower than service price", exception.getMessage());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void saveOrUpdateProposal_shouldSaveOrder_whenProposedPriceIsValid() {
        SaveOrUpdateOrder dto = mock(SaveOrUpdateOrder.class);
        Order order = mock(Order.class);
        Service service = mock(Service.class);

        when(orderMapper.dtoToEntity(dto)).thenReturn(order);
        when(order.getProposedPrice()).thenReturn((int) 150_000D);
        when(order.getService()).thenReturn(service);
        when(service.getBasePrice()).thenReturn((int) 100_000D);

        Order result = orderService.saveOrUpdateProposal(dto);

        verify(orderRepository).beginTransaction();
        verify(orderRepository).save(order);
        verify(orderRepository).commitTransaction();
        assertEquals(order, result);
    }
}
