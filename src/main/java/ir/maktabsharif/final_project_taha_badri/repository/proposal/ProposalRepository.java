package ir.maktabsharif.final_project_taha_badri.repository.proposal;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Order;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ProposalStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ProposalRepository
        extends JpaRepository<Proposal, Long>, JpaSpecificationExecutor<Proposal> {
    List<Proposal> findByOrderIdOrderBySuggestPriceAsc(Long orderId);

    @Query("""
            select p from Proposal p where p.order = :order  order by p.expert.score desc
            """)
    List<Proposal> findByOrderIdOrderByExpertScoreDesc(Long orderId);

    @Modifying
    @Query("""
            UPDATE Order o SET o.orderStatus = 'WAITING_FOR_EXPERT_TO_VISIT',
                o.expert =(SELECT p.expert FROM Proposal p WHERE p.id = :proposalId),
                o.finalPrice = (SELECT p.suggestPrice from Proposal p where p.id = :proposalId),
                o.endDate =(SELECT p.endDate FROM Proposal p WHERE p.id = :proposalId)
            WHERE o = (SELECT p.order FROM Proposal p WHERE p.id = :proposalId)
            """)
    void setOrderWithProposal(@Param("proposalId") Long proposalId);

    @Modifying
    @Query("""
            update Proposal p set p.status= :status where p.order.id =: orderId
            """)
    void changeAllProposalStatusByOrderId(@Param("orderId") Long orderId, @Param("status") ProposalStatus status);

    @Modifying
    @Transactional
    @Query("""
                UPDATE Order o
                SET o.orderStatus = 'STARTED'
                WHERE o = (
                    SELECT p.order FROM Proposal p
                    WHERE p.id = :proposalId
                      AND p.startWork < :now
                )
            """)
    void setOrderStatusToStartedByProposalId(@Param("proposalId") Long proposalId, @Param("now") ZonedDateTime now);

    @Modifying
    @Query("""
            update Proposal p set p.status= :status where p.id=:proposalId
            """)
    void changeStatusById(@Param("proposalId") Long proposalId, @Param("status") ProposalStatus status);

    List<Order> findAllOrdersByExpert_Id(Long expertId);



}


