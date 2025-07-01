package ir.maktabsharif.final_project_taha_badri.repository.address;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AddressRepositoryImpl
        extends CrudRepositoryImpl<Address, Long>
        implements AddressRepository {
    public AddressRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Address> getDomainClass() {
        return Address.class;
    }
}
