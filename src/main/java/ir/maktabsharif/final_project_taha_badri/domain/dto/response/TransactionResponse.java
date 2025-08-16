package ir.maktabsharif.final_project_taha_badri.domain.dto.response;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.enums.TransactionalStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

import java.time.ZonedDateTime;


public record TransactionResponse(

        ZonedDateTime createDate,

        ZonedDateTime lastUpdateDate,

        @Getter
        Long id,

        Double amount,

        Long customerId,

        Long expertId,

        TransactionalStatus status
) implements Identifiable<Long> {
}
