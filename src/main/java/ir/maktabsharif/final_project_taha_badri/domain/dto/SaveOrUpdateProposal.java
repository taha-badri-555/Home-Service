package ir.maktabsharif.final_project_taha_badri.domain.dto;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.ZonedDateTime;

public record SaveOrUpdateProposal(
        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id is not null.")
        Long id,

        @NotNull(groups = ValidationGroup.Save.class, message = "suggestPrice is null.")
        @Min(value = 0, message = "suggestPrice is less than one.")
        Integer suggestPrice,

        @NotNull(groups = ValidationGroup.Save.class, message = "startWork is null.")
        ZonedDateTime startWork,

        @NotNull(groups = ValidationGroup.Save.class, message = "duration is null.")
        @Min(value = 0, message = "duration is less than one.")
        Double duration,

        @NotNull(groups = ValidationGroup.Save.class, message = "expert is null.")
        Expert expert,

        @NotNull(groups = ValidationGroup.Save.class, message = "order is null.")
        Order order
) {
}
