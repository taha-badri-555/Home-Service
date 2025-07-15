package ir.maktabsharif.final_project_taha_badri.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record SaveOrUpdateFeedback(
        @NotNull(groups = ValidationGroup.Update.class,message = "id is null.")
        @Null(groups = ValidationGroup.Save.class,message = "id must be null.")
        Long id,

        @Min(value = 1,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @Max(value = 5,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        Byte rating,

        String feedback,

        @NotNull(groups = ValidationGroup.Save.class,message = "orderId is null.")
        Long orderId
)implements Identifiable<Long> {
        @Override
        public Long getId() {
                return id;
        }
}
