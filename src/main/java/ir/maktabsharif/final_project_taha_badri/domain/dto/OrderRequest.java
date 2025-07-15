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
public class OrderRequest {
    private String description;
    private Double proposedPrice;
    private ZonedDateTime startDate;
    private OrderStatus orderStatus;
    private String serviceName;
    private String province;
    private String city;
    private String detail;

}
