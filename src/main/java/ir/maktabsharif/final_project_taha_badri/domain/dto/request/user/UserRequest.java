package ir.maktabsharif.final_project_taha_badri.domain.dto.request.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.util.Utility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

public record UserRequest(
        @Getter
        @NotNull(groups = ValidationGroup.Update.class)
        @Null(groups = ValidationGroup.Save.class)
        Long id,

        @NotNull(groups = ValidationGroup.Save.class)
        @NotBlank(message = "firstName is blank.",groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String firstName,

        @NotNull(groups = ValidationGroup.Save.class)
        @NotBlank(message = "lastName is blank.",groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String lastName,

        @NotNull(groups = ValidationGroup.Save.class)
        @NotBlank(message = "password is blank.",groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String password,

        @NotNull(groups = ValidationGroup.Save.class)
        @NotBlank(message = "email is blank.",groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String email
) implements Identifiable<Long> {
    public UserRequest {
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
