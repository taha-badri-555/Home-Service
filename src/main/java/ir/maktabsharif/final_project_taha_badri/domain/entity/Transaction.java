package ir.maktabsharif.final_project_taha_badri.domain.entity;

import ir.maktabsharif.final_project_taha_badri.domain.entity.base.BaseEntity;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Customer;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.TransactionalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Transaction.TABLE_NAME)
public class Transaction extends BaseEntity<Long> {
    public static final String TABLE_NAME = "transaction";

    public static final String AMOUNT_COLUMN = "amount";
    public static final String TRANSACTION_STATUS_COLUMN = "status";


    @Column(name = AMOUNT_COLUMN)
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Expert expert;

    @Column(name = TRANSACTION_STATUS_COLUMN)
    @Enumerated(EnumType.STRING)
    private TransactionalStatus status;
}
