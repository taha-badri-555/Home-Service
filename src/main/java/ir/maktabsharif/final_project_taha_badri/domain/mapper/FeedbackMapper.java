package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.FeedbackRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.FeedbackResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface FeedbackMapper extends BaseMapper<FeedbackRequest, FeedbackResponse, Feedback, Long> {


    //    @Mapping(target = "order", ignore = true)
    @Override
    Feedback requestToEntity(FeedbackRequest dto);


    //    @Mapping(target = "orderId", ignore = true)

    @Override
    @Mapping(source = "order.id", target = "orderId")
    FeedbackRequest entityToRequest(Feedback entity);


    //    @Mapping(target = "order", ignore = true)
    @Override
    void updateEntityWithRequest(FeedbackRequest dto, @MappingTarget Feedback entity);

    @Override
    Feedback responseToEntity(FeedbackResponse dto);

    @Override
    @Mapping(source = "order.id", target = "orderId")
    FeedbackResponse entityToResponse(Feedback entity);
}
