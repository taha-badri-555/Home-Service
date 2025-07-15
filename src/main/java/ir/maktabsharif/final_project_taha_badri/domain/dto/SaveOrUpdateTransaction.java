package ir.maktabsharif.final_project_taha_badri.domain.dto;

import ir.maktabsharif.final_project_taha_badri.domain.enums.TransactionalStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.postgresql.core.TransactionState;

public record SaveOrUpdateTransaction(

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
        @Override
        public Long getId() {
                return id;
        }
}
