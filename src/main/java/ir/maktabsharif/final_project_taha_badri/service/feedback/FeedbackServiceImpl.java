package ir.maktabsharif.final_project_taha_badri.service.feedback;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateFeedback;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.FeedbackMapper;
import ir.maktabsharif.final_project_taha_badri.repository.feedback.FeedbackRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FeedbackServiceImpl
        extends BaseServiceImpl<
        Feedback,
        Long,
        FeedbackRepository,
        FeedbackMapper,
        SaveOrUpdateFeedback>
        implements FeedbackService {

    public FeedbackServiceImpl(FeedbackRepository repository, FeedbackMapper mapper) {
        super(repository, mapper);
    }
}
