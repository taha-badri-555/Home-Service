package ir.maktabsharif.final_project_taha_badri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ir.maktabsharif.final_project_taha_badri.service.user.managert.ManagerService;
import ir.maktabsharif.final_project_taha_badri.service.user.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Properties;

@SpringBootApplication
@EnableWebSecurity
@RequiredArgsConstructor
public class FinalProjectTahaBadriApplication {
private final  UserService userService;
private final  ManagerService managerService;
    public static void main(String[] args) {
        SpringApplication.run(FinalProjectTahaBadriApplication.class, args);

    }
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate6Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
