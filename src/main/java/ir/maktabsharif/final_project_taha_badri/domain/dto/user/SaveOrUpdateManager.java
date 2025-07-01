package ir.maktabsharif.final_project_taha_badri.domain.dto.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.*;

public record SaveOrUpdateManager (
        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id is not null.")
        Long id,

        @NotBlank(message = "firstName is blank." )
        @NotNull(groups = ValidationGroup.Save.class,message = "firstName is null.")
        String firstName,

        @NotBlank(message = "lastName is blank." )
        @NotNull(groups = ValidationGroup.Save.class,message = "lastName is null.")
        String lastName,

        @NotBlank(message = "password is blank.")
        @NotNull(groups = ValidationGroup.Save.class, message = "password is null.")
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must contain both letters and numbers.")
        String password,

        @NotBlank(message = "email is blank." )
        @NotNull(groups = ValidationGroup.Save.class,message = "email is null.")
        String email
) {
}
