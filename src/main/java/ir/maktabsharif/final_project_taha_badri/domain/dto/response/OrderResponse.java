package ir.maktabsharif.final_project_taha_badri.domain.dto.response;

import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Objects;

@Builder
public record OrderResponse(

        @Getter
        Long id,

        ZonedDateTime createDate,

        ZonedDateTime lastUpdateDate,

        String description,

        Double proposedPrice,

        ZonedDateTime startDate,

        OrderStatus orderStatus,

        String serviceName,

        String province,

        String city,

        String detail,

        String customerName,

        String customerLastName,

        String customerEmail
) implements Identifiable<Long> {

    public OrderResponse(Order order, Long expertId) {
        this(
                order.getId(),
                order.getCreateDate(),
                order.getLastUpdateDate(),
                order.getDescription(),
                order.getProposedPrice(),
                order.getStartDate(),
                order.getOrderStatus(),
                order.getService().getName(),
                order.getAddress().getProvince(),
                order.getAddress().getCity(),
                order.getAddress().getDetail(),
                shouldIncludeCustomer(order.getExpert(), expertId) ? order.getCustomer().getFirstName() : null,
                shouldIncludeCustomer(order.getExpert(), expertId) ? order.getCustomer().getLastName() : null,
                shouldIncludeCustomer(order.getExpert(), expertId) ? order.getCustomer().getEmail() : null
        );
    }

    private static boolean shouldIncludeCustomer(Expert expert, Long expertId) {
        return expert != null && expert.getId() != null && Objects.equals(expert.getId(), expertId);
    }

}

