package ir.maktabsharif.final_project_taha_badri.domain.dto.request;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

public record WalletRequest(
        @Getter
        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id must be null.")
        Long id,

        @NotNull(groups = ValidationGroup.Save.class, message = "amount is null.")
        @Min(value = 0,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        Double amount,
        @NotNull(groups = ValidationGroup.Save.class, message = "userId is null.")
        Long userId
)implements Identifiable<Long> {
}
