package ir.maktabsharif.final_project_taha_badri.domain.entity.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@MappedSuperclass
@NoArgsConstructor
@Getter
public class BaseEntity<ID> {

    public static final String ID = "id";
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = ID)
    private ID id;

    @Column(name = CREATE_DATE)
    @Setter
    private ZonedDateTime createDate;

    @Column(name = LAST_UPDATE_DATE)
    @Setter
    private ZonedDateTime lastUpdateDate;

    @PrePersist
    public void prePersist() {
        setCreateDate(ZonedDateTime.now());
        setLastUpdateDate(ZonedDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        setLastUpdateDate(ZonedDateTime.now());
    }
}
