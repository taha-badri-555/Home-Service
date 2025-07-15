package ir.maktabsharif.final_project_taha_badri.domain.entity;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ProposalStatus;
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
    public static final String PROPOSAL_STATUS_COLUMN = "status";
    public static final String END_DATE_COLUMN = "end_date";



    @Column(name = SUGGEST_PRICE_COLUMN)
    private Double suggestPrice;

    @Column(name = START_DATE_WORK_COLUMN)
    private ZonedDateTime startWork;

    @Column(name = END_DATE_COLUMN)
    private ZonedDateTime endDate;

    @Column(name = DURATION_WORK_COLUMN)
    private Long duration;

    @ManyToOne(fetch = FetchType.LAZY)
    private Expert expert;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Column(name = PROPOSAL_STATUS_COLUMN)
    @Enumerated(EnumType.STRING)
    private ProposalStatus status;
}
