package ir.maktabsharif.final_project_taha_badri.domain.dto.request.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.*;

public record CustomerUpdate(

        @NotBlank(message = "firstName is blank."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class} )
        @NotNull(groups = ValidationGroup.Save.class,message = "firstName is null.")
        String firstName,

        @NotBlank(message = "lastName is blank."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class} )
        @NotNull(groups = ValidationGroup.Save.class,message = "lastName is null.")
        String lastName,

        @NotBlank(message = "password is blank."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "password is null.")
        @Size(min = 8, message = "Password must be at least 8 characters long."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must contain both letters and numbers."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String password, @Email
        @NotBlank(message = "email is blank." )
        @NotNull(groups = ValidationGroup.Save.class,message = "email is null.")
        String email
) implements Identifiable<Long> {
    @Override
    public Long getId() {
        return null;
    }
    public CustomerUpdate {
        email = email.toLowerCase();
    }
}
