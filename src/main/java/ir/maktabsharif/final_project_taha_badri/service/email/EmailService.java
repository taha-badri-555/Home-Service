package ir.maktabsharif.final_project_taha_badri.service.email;

import ir.maktabsharif.final_project_taha_badri.domain.entity.EmailVerificationToken;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;

public interface EmailService {
    void saveToken(EmailVerificationToken verificationToken);

    void sendSimpleMessage(String to, String subject, String text);

    void sendVerificationEmail(String toEmail, String token);

    void createAndSendVerificationMail(Person baseUser);

    String verifyEmail(String token);
}
