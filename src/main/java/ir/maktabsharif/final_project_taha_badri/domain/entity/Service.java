package ir.maktabsharif.final_project_taha_badri.domain.entity;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Service.TABLE_NAME)
public class Service extends BaseEntity<Long> {
    public static final String TABLE_NAME = "service";

    public static final String NAME_COLUMN = "name";
    public static final String BASE_PRICE_COLUMN = "base_price";
    public static final String DESCRIPTION_COLUMN = "description";


    @Column(name = NAME_COLUMN, unique = true)
    private String name;

    @Column(name = BASE_PRICE_COLUMN)
    private Double basePrice;

    @Column(name = DESCRIPTION_COLUMN, columnDefinition = "VARCHAR")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Service parentService;
}
