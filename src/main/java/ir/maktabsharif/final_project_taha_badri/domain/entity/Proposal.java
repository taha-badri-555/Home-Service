package ir.maktabsharif.final_project_taha_badri.domain.entity;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Proposal.TABLE_NAME)
public class Proposal extends BaseEntity<Long> {
    public static final String TABLE_NAME = "proposal";

    public static final String SUGGEST_PRICE_COLUMN = "suggest_price";
    public static final String START_DATE_WORK_COLUMN = "start_date";
    public static final String DURATION_WORK_COLUMN = "duration";



    @Column(name = SUGGEST_PRICE_COLUMN)
    private Integer suggestPrice;

    @Column(name = START_DATE_WORK_COLUMN)
    private ZonedDateTime startWork;

    @Column(name = DURATION_WORK_COLUMN)
    private Double duration;

    @ManyToOne(fetch = FetchType.LAZY)
    private Expert expert;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
}
