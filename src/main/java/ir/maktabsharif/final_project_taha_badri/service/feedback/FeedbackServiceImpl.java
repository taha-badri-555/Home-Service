package ir.maktabsharif.final_project_taha_badri.service.feedback;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateFeedback;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.BaseMapper;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.FeedbackMapper;
import ir.maktabsharif.final_project_taha_badri.repository.feedback.FeedbackRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.order.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FeedbackServiceImpl
        extends BaseServiceImpl<
        Feedback,
        Long,
        FeedbackRepository,
        SaveOrUpdateFeedback,
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
    public Double getScoresAVGByExpertId(Long expertId) {
        return repository.getScoresAVGByExpertId(expertId);
    }

    @Override
    public Byte getScoreByOrderAndExpertId(Long orderId, Long expertId) {
        return repository.getScoreByOrderAndExpertId(orderId, expertId);
    }

    @Override
    protected void setEntityRelations(Feedback entity, SaveOrUpdateFeedback dto) {
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
}
