package ir.maktabsharif.final_project_taha_badri.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record SaveOrUpdateAddress(

        @NotNull(groups = ValidationGroup.Update.class,message = "id is null.")
        @Null(groups = ValidationGroup.Save.class,message = "id is not null.")
        Long id,

        String province,


        String city,


        String detail
) {
}
