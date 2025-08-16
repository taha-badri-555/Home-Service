package ir.maktabsharif.final_project_taha_badri.domain.dto.request;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

import java.time.ZonedDateTime;

public record OrderRequest(
        @Getter
        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id must be null.")
        Long id,

        @NotBlank(message = "description is blank.")
        @NotNull(groups = ValidationGroup.Save.class, message = "description is null.")
        String description,

        @NotBlank(message = "proposedPrice is blank.")
        @NotNull(groups = ValidationGroup.Save.class, message = "proposedPrice is null.")
        Double proposedPrice,

        @Null(groups = ValidationGroup.Update.class)
        Double finalPrice,

        @Future(message = "start date must be in the future."
                , groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "startDate is null.")
        ZonedDateTime startDate,

        @Null(groups = ValidationGroup.Update.class)
        OrderStatus orderStatus,

        @NotNull(groups = ValidationGroup.Save.class, message = "serviceId is null.")
        Long serviceId,

        @NotNull(groups = ValidationGroup.Save.class, message = "addressId is null.")
        Long addressId,

        @Null(groups = ValidationGroup.Update.class)
        Long expertId
) implements Identifiable<Long> {
    public OrderRequest(Long id, OrderStatus orderStatus) {
        this(
                id,
                null,
                null,
                null,
                null,
                orderStatus,
                null,
                null,
                null)
        ;
    }
}
