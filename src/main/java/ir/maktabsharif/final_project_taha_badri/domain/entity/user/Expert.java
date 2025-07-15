package ir.maktabsharif.final_project_taha_badri.domain.entity.user;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseUser;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("EXPERT")
@AllArgsConstructor
public class Expert extends BaseUser {
    public static final String IMAGE_PATH_COLUMN = "image_path";
    public static final String STATUS_COLUMN = "status";
    public static final String SCORE_COLUMN = "score";
    public static final String SUM_SCORE_COLUMN = "sum_score";

    @Column(name = IMAGE_PATH_COLUMN)
    private String imagePath;

    @Column(name = SCORE_COLUMN)
    private Double score;

    @Column(name = SUM_SCORE_COLUMN)
    private Double avgScore;

    @Column(name = STATUS_COLUMN)
    @Enumerated(EnumType.STRING)
    private ExpertStatus status;

    @ManyToMany
    @JoinTable(
            name = "expert_service,",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services;


}
