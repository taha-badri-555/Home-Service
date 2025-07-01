package ir.maktabsharif.final_project_taha_badri.domain.dto;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;

public record SaveOrUpdateService(

        @NotNull(groups = ValidationGroup.Update.class,message = "id is null.")
        @Null(groups = ValidationGroup.Save.class,message = "id is not null.")
        Long id,

        @NotBlank(message = "name is blank.")
        String name,

        @Min(0)
        Integer basePrice,

        @NotBlank(message = "description is blank.")
        String description,


        Service service,


        List<Expert> experts

) {
}
