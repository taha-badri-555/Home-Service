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
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity<Long> {

    public static final String TABLE_NAME = "orders";

    public static final String DESCRIPTION_COLUMN = "description";
    public static final String PROPOSED_PRICE_COLUMN = "proposed_price";
    public static final String START_DATE_COLUMN = "start_date";
    public static final String ORDER_STATUS_COLUMN = "order_status";
    public static final String FINAL_PRICE_COLUMN = "final_price";
    public static final String END_DATE_COLUMN = "end_date";



    @Column(name = DESCRIPTION_COLUMN)
    private String description;

    @Column(name = PROPOSED_PRICE_COLUMN)
    private Double proposedPrice;

    @Column(name = FINAL_PRICE_COLUMN)
    private Double finalPrice;

    @Column(name = START_DATE_COLUMN)
    private ZonedDateTime startDate;

    @Column(name = END_DATE_COLUMN)
    private ZonedDateTime endDate;

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
}
