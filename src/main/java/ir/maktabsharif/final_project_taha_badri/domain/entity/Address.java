package ir.maktabsharif.final_project_taha_badri.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import jakarta.persistence.*;
import lombok.*;

@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Address.TABLE_NAME)
@EqualsAndHashCode(callSuper = false)
public class Address extends BaseEntity<Long> {
    public static final String TABLE_NAME = "address";

    public static final String PROVINCE_COLUMN = "province";
    public static final String CITY_COLUMN = "city";
    public static final String DETAIL_COLUMN = "detail";


    @Column(name =PROVINCE_COLUMN )
    private String province;

    @Column(name =CITY_COLUMN )
    private String city;

    @Column(name =DETAIL_COLUMN )
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
}
