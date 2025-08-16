package ir.maktabsharif.final_project_taha_badri.domain.entity.user;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("CUSTOMER")
public class Customer extends Person {
}
