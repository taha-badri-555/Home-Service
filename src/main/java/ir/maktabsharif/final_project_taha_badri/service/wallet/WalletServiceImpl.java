package ir.maktabsharif.final_project_taha_badri.service.wallet;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.FeedbackRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.OrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.TransactionRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.WalletRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.WalletResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Wallet;
import ir.maktabsharif.final_project_taha_badri.domain.enums.OrderStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.TransactionalStatus;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.WalletMapper;
import ir.maktabsharif.final_project_taha_badri.exception.InsufficientBalanceException;
import ir.maktabsharif.final_project_taha_badri.repository.wallet.WalletRepository;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseServiceImpl;
import ir.maktabsharif.final_project_taha_badri.service.feedback.FeedbackService;
import ir.maktabsharif.final_project_taha_badri.service.order.OrderService;
import ir.maktabsharif.final_project_taha_badri.service.transaction.TransactionService;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class WalletServiceImpl
        extends BaseServiceImpl<
        Wallet,
        Long,
        WalletRepository,
        WalletRequest,
        WalletResponse,
        WalletMapper>
        implements WalletService {
    private final OrderService orderService;
    private final TransactionService transactionService;
    private final UserService userService;
    private final FeedbackService feedbackService;

    public WalletServiceImpl(
            WalletRepository repository,
            WalletMapper mapper,
            @Lazy OrderService orderService,
            TransactionService transactionService,
            UserService userService, FeedbackService feedbackService) {
        super(repository, mapper);
        this.orderService = orderService;
        this.transactionService = transactionService;
        this.userService = userService;
        this.feedbackService = feedbackService;
    }

    @Transactional
    @Override
    protected void setEntityRelations(Wallet entity, WalletRequest dto) {
        if (dto.userId() != null) {
            entity.setUser(userService.findById(dto.userId()));
        }

    }

    @Transactional
    @Override
    public void addCreditToWallet(Double credit, Long userId) {
        Wallet wallet = mapper.responseToEntity(findByUser_Id(userId));
        wallet.setAmount(wallet.getAmount() + credit);
        repository.save(wallet);
        TransactionRequest transaction =
                new TransactionRequest(
                        null,
                        wallet.getAmount() + credit, userId,
                        null,
                        TransactionalStatus.COMPLETED);
        transactionService.save(transaction);
    }

    @Transactional
    @Override
    public WalletResponse payFromWallet(Long customerId, FeedbackRequest feedback) {
        Long id = orderService.findById(feedback.orderId()).getCustomer().getId();
        if (!Objects.equals(customerId, id)) {
            throw new IllegalArgumentException("this order is not for the customer with id: " + customerId);
        }
        Order order = orderService.findById(feedback.orderId());
        Wallet customerWallet = mapper.responseToEntity(findByUser_Id(order.getCustomer().getId()));
        Wallet expertWallet = mapper.responseToEntity(findByUser_Id(order.getExpert().getId()));
        Double price = order.getFinalPrice();
        if (customerWallet == null || customerWallet.getAmount() == null || customerWallet.getAmount() < price) {
            transactionService.save(
                    new TransactionRequest(
                            null,
                            expertWallet.getAmount(),
                            feedback.orderId(),
                            expertWallet.getUser().getId(),
                            TransactionalStatus.CANCELED)

            );
            throw new InsufficientBalanceException();
        }
        Double customerNewBalance = customerWallet.getAmount() - price;
        Double expertNewBalance = expertWallet.getAmount() + (price * 0.7);
        customerWallet.setAmount(customerNewBalance);
        expertWallet.setAmount(expertNewBalance);
        orderService.update(new OrderRequest(order.getId(), OrderStatus.PAYED));
        repository.save(customerWallet);
        repository.save(expertWallet);
        transactionService.save(
                new TransactionRequest(
                        null,
                        expertWallet.getAmount(),
                        feedback.orderId(),
                        expertWallet.getUser().getId(),
                        TransactionalStatus.COMPLETED)

        );
        feedbackService.save(feedback);
        return mapper.entityToResponse(customerWallet);

    }

    @Transactional(readOnly = true)
    @Override
    public WalletResponse findByUser_Id(Long userId) {
        return mapper.entityToResponse(repository.findByUser_Id(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public Double getAmount(Long userId) {
        return repository.getAmount(userId);
    }
}
