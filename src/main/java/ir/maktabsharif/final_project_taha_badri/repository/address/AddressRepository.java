package ir.maktabsharif.final_project_taha_badri.repository.address;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository
        extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
@Modifying
@Query("""
        update Address a  set a.customer=:customer where a.id=:addressId
        """)
    void addAddressToCustomer(Customer customer, Long addressId);
}
