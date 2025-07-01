package ir.maktabsharif.final_project_taha_badri.domain.dto;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import jakarta.validation.constraints.*;

import java.time.ZonedDateTime;

public record SaveOrUpdateOrder(

        @NotNull(groups = ValidationGroup.Update.class, message = "id is null.")
        @Null(groups = ValidationGroup.Save.class, message = "id is not null.")
        Long id,

        @NotBlank(message = "description is blank.")
        @NotNull(groups = ValidationGroup.Save.class, message = "description is null.")
        String description,

        @NotBlank(message = "proposedPrice is blank.")
        @NotNull(groups = ValidationGroup.Save.class, message = "proposedPrice is null.")
        Integer proposedPrice,

        @Future(message = "start date must be in the future.")
        @NotNull(groups = ValidationGroup.Save.class, message = "startDate is null.")
        ZonedDateTime startDate,

        @NotNull(groups = ValidationGroup.Save.class, message = "order status is null.")
        OrderStatus orderStatus,

        @NotNull(groups = ValidationGroup.Save.class, message = "customer is null.")
        Customer customer,

        @NotNull(groups = ValidationGroup.Save.class, message = "service is null.")
        Service service,

        @NotNull(groups = ValidationGroup.Save.class, message = "address is null.")
        Address address,

        @NotNull(groups = ValidationGroup.Save.class, message = "expert is null.")
        Expert expert
) {
}
