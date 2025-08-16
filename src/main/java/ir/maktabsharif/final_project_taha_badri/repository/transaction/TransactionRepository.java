package ir.maktabsharif.final_project_taha_badri.repository.transaction;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    Page<Transaction> findByCustomerIdOrExpertId(Long customerId, Long expertId, Pageable pageable);

}
