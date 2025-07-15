package ir.maktabsharif.final_project_taha_badri.domain.dto.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import jakarta.validation.constraints.*;

public record SaveOrUpdateExpert(
        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id must be null..")
        Long id,

        @NotBlank(message = "firstName is blank."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "firstName is null.")
        @Null(groups = ValidationGroup.Update.class, message = "firstName must be null..")
        String firstName,

        @NotBlank(message = "lastName is blank."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "lastName is null.")
        @Null(groups = ValidationGroup.Update.class, message = "lastName must be null..")
        String lastName,

        @NotBlank(message = "password is blank.",groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "password is null.")
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must contain both letters and numbers."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String password,

        @Email(groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotBlank(message = "email is blank.",groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "email is null.")
        String email,

        @Pattern(regexp = ".*\\.(jpg|jpeg|png)$",
                message = "Image path must end with .jpg, .jpeg, or .png")
        String imagePath,

        @NotNull(groups = ValidationGroup.Update.class, message = "score is null.")
        @Null(groups = ValidationGroup.Save.class, message = "score must be null..")
        Double score,

        ExpertStatus status,

        Double avgScore
) implements Identifiable<Long> {
        @Override
        public Long getId() {
                return id;
        }
        public SaveOrUpdateExpert {
                email = email.toLowerCase();
        }

        public SaveOrUpdateExpert(Long id, ExpertStatus status) {
                this(id, null, null, null, null, null, 0.0, status, null);
        }

        public SaveOrUpdateExpert(Long id, Double avgScore) {
                this(id, null, null, null, null, null, 0.0, null, avgScore);
        }
}
