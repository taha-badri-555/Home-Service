package ir.maktabsharif.final_project_taha_badri.repository.wallet;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository
        extends JpaRepository<Wallet, Long>, JpaSpecificationExecutor<Wallet> {

    Wallet findByUser_Id(Long userId);
}
