package ir.maktabsharif.final_project_taha_badri.repository.wallet;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import ir.maktabsharif.final_project_taha_badri.repository.base.crud.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class WalletRepositoryImpl
        extends CrudRepositoryImpl<Wallet, Long>
        implements WalletRepository {

    public WalletRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Wallet> getDomainClass() {
        return Wallet.class;
    }
}
