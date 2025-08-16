package ir.maktabsharif.final_project_taha_badri.domain.dto.request;

import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;

import java.time.ZonedDateTime;

public record SearchOrderRequest(

        Long id,

        String role,

        ZonedDateTime startDate,

        ZonedDateTime endDate,

        OrderStatus status,

        String serviceName

) {
}
