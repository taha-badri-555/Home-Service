package ir.maktabsharif.final_project_taha_badri.service.email;

import ir.maktabsharif.final_project_taha_badri.domain.entity.EmailVerificationToken;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import ir.maktabsharif.final_project_taha_badri.domain.entity.user.Expert;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.Role;
import ir.maktabsharif.final_project_taha_badri.domain.mapper.user.UserMapper;
import ir.maktabsharif.final_project_taha_badri.repository.email.EmailVerificationTokenRepository;
import ir.maktabsharif.final_project_taha_badri.service.user.expert.ExpertService;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailVerificationTokenRepository repository;
    private final JavaMailSender mailSender;
    private final UserService userService;
    private final JavaMailSender emailSender;
    private final UserMapper mapper;
    private final ExpertService expertService;

    @Override
    public void saveToken(EmailVerificationToken verificationToken) {
        repository.save(verificationToken);
    }


    @Override
    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Tahabadri83@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    @Async
    public void sendVerificationEmail(String toEmail, String token) {
        String subject = "Email Verification Link";
        String confirmationUrl = "http://localhost:8080/api/v1/auth/verify?token=" + token;
        String message = "Please click on this link to activate your account:\n" + confirmationUrl;

        sendSimpleMessage(toEmail, subject, message);
    }

    @Override
    public void createAndSendVerificationMail(Person baseUser) {
        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken(
                token,
                LocalDateTime.now().plusMinutes(30),
                baseUser
        );
        saveToken(verificationToken);

        sendVerificationEmail(baseUser.getEmail(), token);
    }

    @Override
    public String verifyEmail(String token) {
        EmailVerificationToken verificationToken = repository.findEmailVerificationTokenByToken(token)
                .orElseThrow(() -> new IllegalStateException("Invalid verification token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Token expired";
        }

        Person baseUser = verificationToken.getUser();
        baseUser.setVerified(true);
        userService.update(mapper.entityToRequest(baseUser));

        repository.delete(verificationToken);
        if (baseUser.getRole().equals(Role.EXPERT)) {
            if (baseUser.getStatus().equals(ExpertStatus.NEW)) {
                Expert expert = expertService.findById(baseUser.getId());
                if (expert.getImage() != null) {
                    expertService.changeExpertStatus(expert.getId(), ExpertStatus.WAITING);
                }
            }
        }
        return "Account verified successfully";
    }

}