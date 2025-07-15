package ir.maktabsharif.final_project_taha_badri.domain.dto.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Set;

public record SearchRequest(


        String role,


        String firstName,


        String lastName,


        Set<Service> services,

        @Min(value = 0,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @Max(value = 5,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        Byte minRating,

        @Min(value = 0,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @Max(value = 5,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        Byte maxRating
) {
    public SearchRequest {
        if ("CUSTOMER".equals(role)) {
            services = null;
            minRating = null;
            maxRating = null;
        }
    }


}
