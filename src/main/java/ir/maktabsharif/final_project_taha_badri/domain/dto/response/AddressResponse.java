package ir.maktabsharif.final_project_taha_badri.domain.dto.response;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import lombok.Getter;

import java.time.ZonedDateTime;

public record AddressResponse(

        ZonedDateTime createDate,

        ZonedDateTime lastUpdateDate,

        @Getter
        Long id,

        String province,

        String city,

        String detail,

        Long customerId

) implements Identifiable<Long> {

}
