package ir.maktabsharif.final_project_taha_badri.domain.dto.response.user;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.ZonedDateTime;

public record CustomerResponse(

        @Getter
        Long id,

        ZonedDateTime createDate,

        ZonedDateTime lastUpdateDate,

        String firstName,

        String lastName,

        String email,

        Long addressId
) implements Identifiable<Long> {
}
