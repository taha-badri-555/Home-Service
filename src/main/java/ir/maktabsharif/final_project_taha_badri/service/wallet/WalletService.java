package ir.maktabsharif.final_project_taha_badri.service.wallet;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateFeedback;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateWallet;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.stereotype.Service;

public interface WalletService extends BaseService<Wallet, Long, SaveOrUpdateWallet> {
    void addCreditToWallet(Double credit, Long userId);

    Wallet payFromWallet(Long orderId, SaveOrUpdateFeedback feedback);

    Wallet findByUser_Id(Long userId);

}
