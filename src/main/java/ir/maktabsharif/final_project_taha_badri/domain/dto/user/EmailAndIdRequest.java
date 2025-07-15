package ir.maktabsharif.final_project_taha_badri.domain.dto.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmailAndIdRequest(

        @NotNull(message = "id is null."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        Long id,

        @Email
        @NotBlank(message = "email is blank."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "email is null.")
        String email


) {
    public EmailAndIdRequest {
        email = email.toLowerCase();
    }
}
