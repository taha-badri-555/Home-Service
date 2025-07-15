package ir.maktabsharif.final_project_taha_badri.domain.dto.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record SaveOrUpdateUser(

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
    @Override
    public Long getId() {
        return id;
    }
    public SaveOrUpdateUser {
        email = email.toLowerCase();
    }
}
