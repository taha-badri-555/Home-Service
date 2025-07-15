package ir.maktabsharif.final_project_taha_badri.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.ZonedDateTime;

public record SaveOrUpdateProposal(
        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id must be null.")
        Long id,

        @NotNull(groups = ValidationGroup.Save.class, message = "suggestPrice is null.")
        @Min(value = 0, message = "suggestPrice is less than one."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        Double suggestPrice,

        @NotNull(groups = ValidationGroup.Save.class, message = "startWork is null.")
        ZonedDateTime startWork,

        @NotNull(groups = ValidationGroup.Save.class,message = "end date is null.")
        ZonedDateTime endDate,

        @NotNull(groups = ValidationGroup.Save.class, message = "duration is null.")
        @Min(value = 0, message = "duration is less than one.")
        Long duration,

        @NotNull(groups = ValidationGroup.Save.class, message = "expertId is null.")
        Long expertId,

        @NotNull(groups = ValidationGroup.Save.class, message = "orderId is null.")
        Long orderId
) implements Identifiable<Long> {
        @Override
        public Long getId() {
                return id;
        }
}
