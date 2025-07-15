package ir.maktabsharif.final_project_taha_badri.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record SaveOrUpdateService(

        @NotNull(groups = ValidationGroup.Update.class,message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id must be null.")
        Long id,

        @NotBlank(message = "name is blank."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String name,

        @Min(value = 0,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        Double basePrice,

        @NotBlank(message = "description is blank.")
        String description,


        Long serviceId
)implements Identifiable<Long> {
        @Override
        public Long getId() {
                return id;
        }
}
