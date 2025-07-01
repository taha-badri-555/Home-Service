package ir.maktabsharif.final_project_taha_badri.domain.entity.user;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Service;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseUser;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("EXPERT")
public class Expert extends BaseUser {
    public static final String IMAGE_PATH_COLUMN = "image_path";
    public static final String STATUS_COLUMN = "status";

    @Column(name = IMAGE_PATH_COLUMN, nullable = false)
    private String imagePath;

    @Column(name = STATUS_COLUMN, nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpertStatus status;

    @ManyToMany
    @JoinTable(
            name = "expert_service,",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;

    public Expert(String firstName,
                  String lastName,
                  String password,
                  String email,String imagePath, List<Service> services) {
        super(firstName,lastName,password,email);
        this.imagePath = imagePath;
        this.status = ExpertStatus.WAITING;
        this.services = services;
    }

}
