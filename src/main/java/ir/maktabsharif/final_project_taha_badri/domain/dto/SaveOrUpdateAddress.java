package ir.maktabsharif.final_project_taha_badri.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record SaveOrUpdateAddress (

        @NotNull(groups = ValidationGroup.Update.class,message = "id is null.")
        @Null(groups = ValidationGroup.Save.class,message = "id must be null.")
        Long id,


        @NotNull(groups = ValidationGroup.Save.class,message = "province  is null.")
        String province,

        @NotNull(groups = ValidationGroup.Save.class,message = "city  is null.")
        String city,

        @NotNull(groups = ValidationGroup.Save.class,message = "detail  is null.")
        String detail,

        @NotNull(groups = ValidationGroup.Save.class,message = "customerId  is null.")
        Long customerId
) implements Identifiable<Long>{
        @Override
        public Long getId() {
                return id;
        }
}
