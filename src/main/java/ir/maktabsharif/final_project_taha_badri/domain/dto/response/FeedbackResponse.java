package ir.maktabsharif.final_project_taha_badri.domain.dto.response;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

import java.time.ZonedDateTime;

public record FeedbackResponse(
        ZonedDateTime createDate,

        ZonedDateTime lastUpdateDate,

        @Getter
        Long id,

        Byte rating,

        String feedback,

        Long orderId
)implements Identifiable<Long> {
}
