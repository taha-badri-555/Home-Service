package ir.maktabsharif.final_project_taha_badri.service.transaction;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.TransactionRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.TransactionResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Transaction;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.TransactionMapper;
import ir.maktabsharif.final_project_taha_badri.repository.transaction.TransactionRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.user.customer.CustomerService;
import ir.maktabsharif.final_project_taha_badri.service.user.expert.ExpertService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class TransactionServiceImpl
        extends BaseServiceImpl<
        Transaction,
        Long,
        TransactionRepository,
        TransactionRequest,
        TransactionResponse,
        TransactionMapper>
        implements TransactionService {
    private final ExpertService expertService;
    private final CustomerService customerService;

    public TransactionServiceImpl(
            TransactionRepository repository,
            TransactionMapper mapper,
            @Lazy ExpertService expertService,
            @Lazy CustomerService customerService) {
        super(repository, mapper);

        this.expertService = expertService;
        this.customerService = customerService;
    }

    @Override
    protected void setEntityRelations(Transaction entity, TransactionRequest dto) {
        if (dto.expertId() != null) {
            entity.setExpert(expertService.findById(dto.expertId()));
        }
        if (dto.customerId() != null) {
            entity.setCustomer(customerService.findById(dto.customerId()));
        }
    }

    @Override
    public Page<TransactionResponse> findByUserId(Long customerId, Long expertId, Pageable pageable) {
        return repository.findByCustomerIdOrExpertId(customerId, expertId, pageable)
                .map(mapper::entityToResponse);
    }

    @Override
    public TransactionResponse save(TransactionRequest dto) {
        Customer customer = customerService.findById(dto.customerId());
        Expert expert = expertService.findById(dto.expertId());

        Transaction transaction= new Transaction(dto.amount(), customer,expert,dto.status());

        return mapper.entityToResponse(repository.save(transaction));

    }
}
