package ir.maktabsharif.final_project_taha_badri.service.wallet;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateWallet;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface WalletService extends BaseService<Wallet, Long, SaveOrUpdateWallet> {
}
