package ir.maktabsharif.final_project_taha_badri.domain.dto.request.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.*;
import lombok.Getter;

public record ManagerRequest(
        @Getter
        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id must be null..")
        Long id,

        @NotBlank(message = "firstName is blank.",groups = { ValidationGroup.Save.class} )
        String firstName,

        @NotBlank(message = "lastName is blank.",groups = { ValidationGroup.Save.class} )
        String lastName,

        @NotBlank(message = "password is blank."
                ,groups = {ValidationGroup.Save.class})
        @Size(min = 8, message = "Password must be at least 8 characters long."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must contain both letters and numbers."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String password,

        @NotBlank(message = "email is blank." ,groups = { ValidationGroup.Save.class})
        String email
)implements Identifiable<Long> {
        public ManagerRequest {
                email = email.toLowerCase();
        }
}
