package ir.maktabsharif.final_project_taha_badri.domain.dto;

import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.ZonedDateTime;

public record SaveOrUpdateOrder(

        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id must be null.")
        Long id,

        @NotBlank(message = "description is blank.")
        @NotNull(groups = ValidationGroup.Save.class, message = "description is null.")
        String description,

        @NotBlank(message = "proposedPrice is blank.")
        @NotNull(groups = ValidationGroup.Save.class, message = "proposedPrice is null.")
        Double proposedPrice,

        Double finalPrice,

        @Future(message = "start date must be in the future."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotNull(groups = ValidationGroup.Save.class, message = "startDate is null.")
        ZonedDateTime startDate,

        @NotNull(groups = ValidationGroup.Save.class, message = "order status is null.")
        OrderStatus orderStatus,

        @NotNull(groups = ValidationGroup.Save.class, message = "customerId is null.")
        Long customerId,

        @NotNull(groups = ValidationGroup.Save.class, message = "serviceId is null.")
        Long serviceId,

        @NotNull(groups = ValidationGroup.Save.class, message = "addressId is null.")
        Long addressId,

        @NotNull(groups = ValidationGroup.Save.class, message = "expertId is null.")
        Long expertId
)implements Identifiable<Long> {
        @Override
        public Long getId() {
                return id;
        }

        public SaveOrUpdateOrder(Long id, OrderStatus orderStatus) {
                this(
                        id,
                        null,
                        null,
                        null,
                        null,
                        orderStatus,
                        null,
                        null,
                        null,
                        null)
                ;
        }
}
