package ir.maktabsharif.final_project_taha_badri.service.wallet;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateWallet;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.WalletMapper;
import ir.maktabsharif.final_project_taha_badri.repository.wallet.WalletRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class WalletServiceImpl
        extends BaseServiceImpl<
        Wallet,
        Long,
        WalletRepository,
        WalletMapper,
        SaveOrUpdateWallet>
        implements WalletService {

    public WalletServiceImpl(WalletRepository repository, WalletMapper mapper) {
        super(repository, mapper);
    }
}
