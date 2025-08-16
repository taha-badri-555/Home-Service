package ir.maktabsharif.final_project_taha_badri.domain.dto.response;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

public record WalletResponse(
        @Getter
        Long id,

        Double amount,

        Long userId
)implements Identifiable<Long> {
}
