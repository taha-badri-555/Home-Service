package ir.maktabsharif.final_project_taha_badri.domain.entity;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Feedback.TABLE_NAME)
public class Feedback extends BaseEntity <Long>{
    public static final String TABLE_NAME = "feedback";

    public static final String RATING_COLUMN = "rating";
    public static final String FEEDBACK_COLUMN = "feedback";

    @Column(name = RATING_COLUMN)
    private Byte rating;

    @Column(name = FEEDBACK_COLUMN,columnDefinition = "VARCHAR")
    private String feedback;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

}
