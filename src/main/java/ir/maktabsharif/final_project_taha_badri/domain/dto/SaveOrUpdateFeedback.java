package ir.maktabsharif.final_project_taha_badri.domain.dto;

import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import jakarta.validation.constraints.*;

public record SaveOrUpdateFeedback(
        @NotNull(groups = ValidationGroup.Update.class,message = "id is null.")
        @Null(groups = ValidationGroup.Save.class,message = "id is not null.")
        Long id,

        @Min(1)
        @Max(5)
        Byte rating,

        String feedback,

        @NotNull(groups = ValidationGroup.Save.class,message = "expert is null.")
        Expert expert
) {
}
