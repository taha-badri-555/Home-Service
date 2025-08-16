package ir.maktabsharif.final_project_taha_badri.service.feedback;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.FeedbackRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.FeedbackResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;

public interface FeedbackService extends BaseService<Feedback, Long, FeedbackRequest, FeedbackResponse> {

    FeedbackResponse save(Long userId, FeedbackRequest feedbackDTO);

    Double getScoresAVGByExpertId(Long expertId);

    Byte getScoreByOrderAndExpertId(Long orderId, Long expertId);

    Feedback findByOrderIdAndExpertId(Long orderId, Long expertId);

    Long getAllScoreByExpertId(Long expertId);

    Feedback findByOrderId(Long aLong);
}
