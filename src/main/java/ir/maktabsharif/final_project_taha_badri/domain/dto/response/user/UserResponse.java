package ir.maktabsharif.final_project_taha_badri.domain.dto.response.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.util.Utility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

import java.time.ZonedDateTime;

public record UserResponse(
        ZonedDateTime createDate,

        ZonedDateTime lastUpdateDate,

        @Getter
        Long id,

        String firstName,

        String lastName,

        String email
) implements Identifiable<Long> {

    public UserResponse {
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
}
