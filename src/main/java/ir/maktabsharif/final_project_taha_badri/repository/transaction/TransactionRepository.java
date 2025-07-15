package ir.maktabsharif.final_project_taha_badri.repository.transaction;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Address;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Transaction;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findAllTransactionsByCustomerAndExpert(Customer customer, Expert expert);

    List<Transaction> findAllByCustomer(Customer customer);

    List<Transaction> findAllByExpert(Expert expert);
}
