package ir.maktabsharif.final_project_taha_badri.domain.entity;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = Order.TABLE_NAME)

public class Order extends BaseEntity<Long> {

    public static final String TABLE_NAME = "orders";

    public static final String DESCRIPTION_COLUMN = "description";
    public static final String PROPOSED_PRICE_COLUMN = "proposed_price";
    public static final String START_DATE_COLUMN = "start_date";
    public static final String ORDER_STATUS_COLUMN = "order_status";



    @Column(name = DESCRIPTION_COLUMN)
    private String description;

    @Column(name = PROPOSED_PRICE_COLUMN)
    private Integer proposedPrice;

    @Column(name = START_DATE_COLUMN)
    private ZonedDateTime startDate;

    @Column(name = ORDER_STATUS_COLUMN)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Expert expert;

    public Order(
            String description,
            Integer proposedPrice,
            ZonedDateTime startDate,
            Customer customer,
            Service service,
            Address address,
            Expert expert) {
        this.description = description;
        this.proposedPrice = proposedPrice;
        this.startDate = startDate;
        this.orderStatus = OrderStatus.WAITING_FOR_EXPERT_TO_VISIT;
        this.customer = customer;
        this.service = service;
        this.address = address;
        this.expert = expert;
    }
    public Order() {
        this.orderStatus = OrderStatus.WAITING_FOR_EXPERT_TO_VISIT;
    }
}
