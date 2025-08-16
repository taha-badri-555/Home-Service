package ir.maktabsharif.final_project_taha_badri.domain.dto.request;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

public record ServiceRequest(
        @Getter
        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id must be null.")
        Long id,

        @NotBlank(message = "name is blank."
                , groups = {ValidationGroup.Save.class})
        String name,

        @Min(value = 0, groups = {ValidationGroup.Save.class})
        Double basePrice,

        @NotBlank(message = "description is blank.", groups = {ValidationGroup.Save.class})
        String description,


        Long serviceId
) implements Identifiable<Long> {}
