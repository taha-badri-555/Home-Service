package ir.maktabsharif.final_project_taha_badri.domain.dto;

import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AcceptedOrderRequest extends OrderRequest {
    private String customerName;
    private String customerLastName;
    private String customerEmail;

    public AcceptedOrderRequest(
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
            String customerEmail) {
        super(description, proposedPrice, startDate, orderStatus, serviceName, province, city, detail);
        this.customerName = customerName;
        this.customerLastName = customerLastName;
        this.customerEmail = customerEmail;
    }
}
