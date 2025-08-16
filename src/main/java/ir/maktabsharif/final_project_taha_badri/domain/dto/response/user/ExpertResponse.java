package ir.maktabsharif.final_project_taha_badri.domain.dto.response.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.util.Utility;
import lombok.Getter;

import java.time.ZonedDateTime;

public record ExpertResponse
        (ZonedDateTime createDate,

         ZonedDateTime lastUpdateDate,

         @Getter
         Long id,

         String firstName,

         String lastName,

         String email,

         byte[] image,

         Double score,

         ExpertStatus status,

         Double avgScore


        ) implements Identifiable<Long> {

    public ExpertResponse {

        if (email != null) {
            email = email.toLowerCase();
        }
        if (firstName != null) {
            firstName = Utility.formatName(firstName);
        }
        if (lastName != null) {
            lastName = Utility.formatName(lastName);
        }
    }

    public ExpertResponse(ExpertStatus status) {
        this(null, null,null, null, null,  null, null, 0.0, status, null);
    }

    public ExpertResponse(Double avgScore) {
        this(null,null, null, null, null, null, null, 0.0, null, avgScore);
    }

    public ExpertResponse(byte[] imagePath) {
        this(null,null, null, null, null, null,  imagePath, null, null, null);
    }

}

