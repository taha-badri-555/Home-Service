package ir.maktabsharif.final_project_taha_badri.service.feedback;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.FeedbackRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.FeedbackResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.FeedbackMapper;
import ir.maktabsharif.final_project_taha_badri.repository.feedback.FeedbackRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.order.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Transactional
public class FeedbackServiceImpl
        extends BaseServiceImpl<
        Feedback,
        Long,
        FeedbackRepository,
        FeedbackRequest,
        FeedbackResponse,
        FeedbackMapper>
        implements FeedbackService {

    private final OrderService orderService;

    public FeedbackServiceImpl(
            FeedbackRepository repository,
            FeedbackMapper mapper,
            @Lazy OrderService orderService) {
        super(repository, mapper);
        this.orderService = orderService;
    }

    @Override
    public FeedbackResponse save(Long customerId, FeedbackRequest dto) {
        if (repository.findByOrder_Id(dto.orderId()).isPresent()) {

            if (orderService.findById(dto.orderId()).getCustomer() != null) {
                Long feedbackCustomerId = orderService.findById(dto.orderId()).getCustomer().getId();

                if (!customerId.equals(feedbackCustomerId)) {
                    throw new IllegalArgumentException("customer id is not equal to feedback customer id");
                }
            }
            Feedback byOrderId = findByOrderId(dto.orderId());
            byOrderId.setRating((byte) (byOrderId.getRating() + dto.rating()));
            if (dto.feedback() != null) {
                byOrderId.setFeedback(dto.feedback());
            }
            return update(mapper.entityToRequest(byOrderId));
        }
        Feedback feedback = mapper.requestToEntity(dto);
        setEntityRelations(feedback, dto);
        Feedback save = repository.save(feedback);
        return mapper.entityToResponse(save);
    }

    @Override
    public Double getScoresAVGByExpertId(Long expertId) {
        return repository.getScoresAVGByExpertId(expertId);
    }

    @Override
    public Byte getScoreByOrderAndExpertId(Long orderId, Long expertId) {
        return repository.getScoreByOrderAndExpertId(orderId, expertId);
    }

    @Override
    protected void setEntityRelations(Feedback entity, FeedbackRequest dto) {
        if (dto.orderId() != null) {
            Order byId = orderService.findById(dto.orderId());
            entity.setOrder(byId);
        }

    }

    @Override
    public Feedback findByOrderIdAndExpertId(Long orderId, Long expertId) {
        return repository.findByOrderIdAndExpertId(orderId, expertId);
    }

    @Override
    public Long getAllScoreByExpertId(Long expertId) {
        return repository.getAllScoreByExpertId(expertId);
    }

    @Override
    public Feedback findByOrderId(Long orderId) {
        return repository.findByOrder_Id(orderId)
                .orElseThrow(() -> new NoSuchElementException("Feedback with OrderId " + orderId + " not found"));
    }
}
