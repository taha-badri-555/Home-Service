package ir.maktabsharif.final_project_taha_badri.service.transaction;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.TransactionRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.TransactionResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Transaction;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService extends BaseService<Transaction,Long, TransactionRequest,TransactionResponse> {

    Page<TransactionResponse> findByUserId(Long customerId, Long expertId, Pageable pageable);

}
