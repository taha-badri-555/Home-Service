package ir.maktabsharif.final_project_taha_badri.domain.mapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateFeedback;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FeedbackMapper extends BaseMapper<SaveOrUpdateFeedback, Feedback,Long> {
    @Override
    SaveOrUpdateFeedback entityToDto(Feedback entity);

    @Override
    Feedback dtoToEntity(SaveOrUpdateFeedback feedback);

    @Override
    void updateEntityWithDTO(SaveOrUpdateFeedback feedback, @MappingTarget Feedback entity);
}
