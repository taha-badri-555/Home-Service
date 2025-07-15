package ir.maktabsharif.final_project_taha_badri.service.transaction;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateTransaction;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Transaction;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.TransactionMapper;
import ir.maktabsharif.final_project_taha_badri.repository.transaction.TransactionRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerService;
import ir.maktabsharif.final_project_taha_badri.service.user.expert.ExpertService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class TransactionServiceImpl
        extends BaseServiceImpl<
        Transaction,
        Long,
        TransactionRepository,
        SaveOrUpdateTransaction,
        TransactionMapper>
        implements TransactionService {
    private final ExpertService expertService;
    private final CustomerService customerService;

    public TransactionServiceImpl(
            TransactionRepository repository,
            TransactionMapper mapper,
           @Lazy ExpertService expertService,
          @Lazy  CustomerService customerService) {
        super(repository, mapper);

        this.expertService = expertService;
        this.customerService = customerService;
    }

    @Override
    protected void setEntityRelations(Transaction entity, SaveOrUpdateTransaction dto) {
        if (dto.expertId() != null) {
            entity.setExpert(expertService.findById(dto.expertId()));
        }
        if (dto.customerId() != null) {
            entity.setCustomer(customerService.findById(dto.customerId()));
        }
    }

    @Override
    public List<Transaction> findAllByCustomer(Long customerId) {
        return repository.findAllByCustomer(customerService.findById(customerId));
    }

    @Override
    public List<Transaction> findAllByExpert(Long expertId) {
        return repository.findAllByExpert(expertService.findById(expertId));
    }
}
