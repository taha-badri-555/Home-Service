package ir.maktabsharif.final_project_taha_badri.domain.dto.request;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.enums.TransactionalStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;


public record TransactionRequest(

        @Getter
        @NotNull(groups = ValidationGroup.Update.class)
        @Null(groups = ValidationGroup.Save.class)
        Long id,

        @NotNull(groups = ValidationGroup.Save.class)
        Double amount,

        @NotNull(groups = ValidationGroup.Save.class)
        Long customerId,

        @NotNull(groups = ValidationGroup.Save.class)
        Long expertId,

        @NotNull(groups = ValidationGroup.Save.class)
        TransactionalStatus status
)implements Identifiable<Long> {
}
