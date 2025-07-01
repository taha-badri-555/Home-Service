package ir.maktabsharif.final_project_taha_badri;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FinalProjectTahaBadriApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectTahaBadriApplication.class, args);
    }

    @Bean
    public EntityManager entityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FinalProjectTahaBadri");
        return emf.createEntityManager();

    }
}
