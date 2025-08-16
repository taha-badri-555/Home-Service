package ir.maktabsharif.final_project_taha_badri.repository.feedback;

import ir.maktabsharif.final_project_taha_badri.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends
        JpaRepository<Feedback, Long>, JpaSpecificationExecutor<Feedback> {
    @Query("""
            select AVG (f.rating) from Feedback f where f.order.expert.id =:expertId
            """)
    Double getScoresAVGByExpertId(@Param("expertId") Long expertId);

    @Query("""
            select f.rating from Feedback f where f.order.id=:orderId and f.order.expert.id =:expertId
            """
    )
    Byte getScoreByOrderAndExpertId(@Param("orderId") Long orderId, @Param("expertId") Long expertId);

    @Query("""
            SELECT f FROM Feedback f where f.order.id =:orderId and f.order.expert.id =:expertId
            """)
    Feedback findByOrderIdAndExpertId(@Param("orderId") Long orderId,@Param("expertId") Long expertId);

    @Query("""
            SELECT SUM (f.rating) FROM Feedback f
            """)
    Long getAllScoreByExpertId(Long expertId);

    @Query(
            """
select f from Feedback f where f.order.id= :orderId
                    """
    )
    Optional<Feedback> findByOrder_Id(@Param("orderId") Long orderId);
}
