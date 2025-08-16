package ir.maktabsharif.final_project_taha_badri.domain.dto.response.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.ZonedDateTime;

public record ManagerResponse(

        ZonedDateTime createDate,

        ZonedDateTime lastUpdateDate,

        @Getter
        Long id,

        String firstName,

        String lastName,

        String email
)implements Identifiable<Long> {

        public ManagerResponse {
                email = email.toLowerCase();
        }
}
