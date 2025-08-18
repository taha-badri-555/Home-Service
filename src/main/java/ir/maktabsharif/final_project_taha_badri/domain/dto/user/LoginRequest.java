package ir.maktabsharif.final_project_taha_badri.domain.dto.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.*;

public record LoginRequest(

        @Email(groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotBlank(message = "email is blank.",groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "email is null.")
        String email,

        @NotBlank(message = "password is blank.",groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "password is null.")
        @Size(min = 8, message = "Password must be at least 8 characters long."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must contain both letters and numbers."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String password
) {
    public LoginRequest {
        email = email.toLowerCase();
    }
}
