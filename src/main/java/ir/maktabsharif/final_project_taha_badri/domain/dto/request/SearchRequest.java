package ir.maktabsharif.final_project_taha_badri.domain.dto.request;

import ir.maktabsharif.final_project_taha_badri.util.Utility;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SearchRequest(


        String role,


        String firstName,


        String lastName,


        String serviceName,

        @Min(value = 1, message = "Minimum rating cannot be less than 0.")
        @Max(value = 5, message = "Maximum rating cannot be greater than 5.")
        Double minRating,

        @Min(value = 1, message = "Minimum rating cannot be less than 0.")
        @Max(value = 5, message = "Maximum rating cannot be greater than 5.")
        Double maxRating
) {
    public SearchRequest {
        if ("CUSTOMER".equals(role)) {
            serviceName = null;
            minRating = null;
            maxRating = null;
        }
        if (firstName != null) {
            firstName = Utility.formatName(firstName);
        }
        if (lastName != null) {
            lastName = Utility.formatName(lastName);
        }
    }


}
