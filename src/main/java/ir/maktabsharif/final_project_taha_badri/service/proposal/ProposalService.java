package ir.maktabsharif.final_project_taha_badri.service.proposal;

import ir.maktabsharif.final_project_taha_badri.domain.dto.OrderRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.SaveOrUpdateProposal;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ProposalStatus;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProposalService extends BaseService<Proposal,Long, SaveOrUpdateProposal> {
    List<Proposal> findByOrderIdOrderBySuggestPrice(Long orderId);

    void setOrderWithProposal(Long proposalId);

    void setOrderStatusToStartedByProposalId(Long proposalId);

    Proposal save(SaveOrUpdateProposal saveOrUpdateProposal, Long expertId);

    List<Proposal> findByOrderIdOrderByExpertScoreDesc(Long orderId);

    void changeAllProposalStatusByOrderId(@Param("orderId") Long orderId, @Param("status") ProposalStatus status);

    void changeStatusById(@Param("proposalId") Long proposalId, @Param("status") ProposalStatus status);

    void chooseProposal(Long proposalId, Long orderId);

    List<OrderRequest> findAllOrdersByExpert_Id(Long expertId);
}
