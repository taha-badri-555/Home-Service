package ir.maktabsharif.final_project_taha_badri.service.wallet;

import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateFeedback;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateOrder;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateTransaction;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateWallet;
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

@Service
public class WalletServiceImpl
        extends BaseServiceImpl<
        Wallet,
        Long,
        WalletRepository,
        SaveOrUpdateWallet,
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
    protected void setEntityRelations(Wallet entity, SaveOrUpdateWallet dto) {
        if (dto.userId() != null) {
            entity.setUser(userService.findById(dto.userId()));
        }

    }

    @Transactional
    @Override
    public void addCreditToWallet(Double credit, Long userId) {
        Wallet wallet = findByUser_Id(userId);
        wallet.setAmount(wallet.getAmount() + credit);
        repository.save(wallet);
        SaveOrUpdateTransaction transaction =
                new SaveOrUpdateTransaction(
                        null,
                        wallet.getAmount() + credit, userId,
                        null,
                        TransactionalStatus.COMPLETED);
        transactionService.save(transaction);
    }

    @Transactional
    @Override
    public Wallet payFromWallet(Long orderId, SaveOrUpdateFeedback feedback) {
        Order order = orderService.findById(orderId);
        Wallet customerWallet = findByUser_Id(order.getCustomer().getId());
        Wallet expertWallet = findByUser_Id(order.getExpert().getId());
        Double price = order.getFinalPrice();
        if (customerWallet.getAmount() < price) {
            transactionService.save(
                    new SaveOrUpdateTransaction(
                            null,
                            expertWallet.getAmount(),
                            orderId,
                            expertWallet.getUser().getId(),
                            TransactionalStatus.CANCELED)

            );
            throw new InsufficientBalanceException();
        }
        Double customerNewBalance = customerWallet.getAmount() - price;
        Double expertNewBalance = expertWallet.getAmount() + (price * 0.7);
        customerWallet.setAmount(customerNewBalance);
        expertWallet.setAmount(expertNewBalance);
        orderService.update(new SaveOrUpdateOrder(order.getId(), OrderStatus.PAYED));
        repository.save(customerWallet);
        repository.save(expertWallet);
        transactionService.save(
                new SaveOrUpdateTransaction(
                        null,
                        expertWallet.getAmount(),
                        orderId,
                        expertWallet.getUser().getId(),
                        TransactionalStatus.COMPLETED)

        );
        feedbackService.save(feedback);
        return customerWallet;

    }

    @Transactional(readOnly = true)
    @Override
    public Wallet findByUser_Id(Long userId) {
        return repository.findByUser_Id(userId);
    }
}
