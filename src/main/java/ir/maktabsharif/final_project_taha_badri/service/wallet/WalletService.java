package ir.maktabsharif.final_project_taha_badri.service.wallet;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.FeedbackRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.WalletRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.WalletResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;

public interface WalletService extends BaseService<Wallet, Long, WalletRequest, WalletResponse> {
    void addCreditToWallet(Double credit, Long userId);

    WalletResponse payFromWallet(Long customerId, FeedbackRequest feedback);

    WalletResponse findByUser_Id(Long userId);

    Double getAmount(Long userId);

}
