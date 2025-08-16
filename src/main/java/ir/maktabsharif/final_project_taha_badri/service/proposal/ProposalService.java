package ir.maktabsharif.final_project_taha_badri.service.proposal;

import ir.maktabsharif.final_project_taha_badri.domain.dto.response.OrderResponse;
import ir.maktabsharif.final_project_taha_badri.domain.dto.request.ProposalRequest;
import ir.maktabsharif.final_project_taha_badri.domain.dto.response.ProposalResponse;
import ir.maktabsharif.final_project_taha_badri.domain.entity.Proposal;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ProposalStatus;
import ir.maktabsharif.final_project_taha_badri.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


public interface ProposalService extends BaseService<Proposal, Long, ProposalRequest, ProposalResponse> {
    ProposalResponse save(Long ExpertId, ProposalRequest dto);

    Page<ProposalResponse> findByOrderIdOrderBySuggestPrice(Long customerId, Long orderId, Pageable pageable);

    void setOrderWithProposal(Long proposalId);

    void setOrderStatusToStartedByProposalId(Long customerId, Long orderId);

    ProposalResponse save(ProposalRequest proposalDTO);

    Page<ProposalResponse> findByOrderIdOrderByExpertScoreDesc(Long customerId, Long orderId, Pageable pageable);

    void changeAllProposalStatusByOrderId(@Param("orderId") Long orderId, @Param("status") ProposalStatus status);

    void changeStatusById(@Param("proposalId") Long proposalId, @Param("status") ProposalStatus status);

    void chooseProposal(Long customerId, Long proposalId, Long orderId);

    Page<OrderResponse> findAllOrdersByExpert_Id(Long expertId, Pageable pageable);
}
