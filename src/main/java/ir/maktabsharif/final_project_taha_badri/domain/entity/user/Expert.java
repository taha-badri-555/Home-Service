package ir.maktabsharif.final_project_taha_badri.domain.entity.user;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
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
public class Expert extends Person {
    public static final String IMAGE_COLUMN = "image";
    public static final String SCORE_COLUMN = "score";
    public static final String AVG_SCORE_COLUMN = "avg_score";

    @Lob
    @Column(name = IMAGE_COLUMN,length =  300_000)
    private byte[] image;

    @Column(name = SCORE_COLUMN)
    private Double score;

    @Column(name = AVG_SCORE_COLUMN)
    private Double avgScore;

    @ManyToMany
    @JoinTable(
            name = "expert_service,",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services;


}
