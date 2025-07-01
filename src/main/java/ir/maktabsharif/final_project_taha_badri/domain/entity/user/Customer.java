package ir.maktabsharif.final_project_taha_badri.domain.entity.user;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseUser;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("CUSTOMER")
public class Customer extends BaseUser {

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

}
