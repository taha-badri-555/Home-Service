package ir.maktabsharif.final_project_taha_badri.domain.dto.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmailRequest(
        @Email(groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotBlank(message = "email is blank.",groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "email is null.")
        String email
) {
    public EmailRequest {
        email = email.toLowerCase();
    }
}
