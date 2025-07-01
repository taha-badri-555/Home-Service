package ir.maktabsharif.final_project_taha_badri.domain.dto;

import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record SaveOrUpdateWallet(
        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id is not null.")
        Long id,

        @NotNull(groups = ValidationGroup.Save.class, message = "amount is null.")
        @Min(0)
        Double amount,

        Expert expert,

        Customer customer
) {
}
