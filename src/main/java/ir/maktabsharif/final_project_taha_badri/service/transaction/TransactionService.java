package ir.maktabsharif.final_project_taha_badri.service.transaction;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateTransaction;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Transaction;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;

import java.util.List;

public interface TransactionService extends BaseService<Transaction,Long, SaveOrUpdateTransaction> {

    List<Transaction> findAllByCustomer(Long customerId);

    List<Transaction> findAllByExpert(Long expertId);
}
