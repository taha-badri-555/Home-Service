package ir.maktabsharif.final_project_taha_badri.domain.dto.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UserSessionRequest(
        @Min(value = 0,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        Long id,
        @Email(groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String email
) implements Identifiable<Long> {
    @Override
    public Long getId() {
        return id;
    }
}
