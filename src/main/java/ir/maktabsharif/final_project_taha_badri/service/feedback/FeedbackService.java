package ir.maktabsharif.final_project_taha_badri.service.feedback;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateFeedback;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackService extends BaseService<Feedback, Long, SaveOrUpdateFeedback> {
}
