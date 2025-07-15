package ir.maktabsharif.final_project_taha_badri.service.feedback;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateFeedback;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

public interface FeedbackService extends BaseService<Feedback, Long, SaveOrUpdateFeedback> {
    Double getScoresAVGByExpertId(Long expertId);

    Byte getScoreByOrderAndExpertId(Long orderId, Long expertId);

    Feedback findByOrderIdAndExpertId(Long orderId, Long expertId);

    Long getAllScoreByExpertId(Long expertId);
}
