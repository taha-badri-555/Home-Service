package ir.maktabsharif.final_project_taha_badri.repository.email;

import ir.maktabsharif.final_project_taha_badri.domain.entity.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findEmailVerificationTokenByToken(String token);

}