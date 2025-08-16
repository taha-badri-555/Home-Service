package ir.maktabsharif.final_project_taha_badri.repository.wallet;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository
        extends JpaRepository<Wallet, Long>, JpaSpecificationExecutor<Wallet> {
    @Query(
            """
                        select  w from Wallet w where w.user.id=:userId
                    """
    )
    Wallet findByUser_Id(@Param("userId") Long userId);

    @Query("""
            select coalesce(w.amount, 0) from Wallet w where w.user.id = :userId
            """)
    Double getAmount(@Param("userId") Long userId);
}
