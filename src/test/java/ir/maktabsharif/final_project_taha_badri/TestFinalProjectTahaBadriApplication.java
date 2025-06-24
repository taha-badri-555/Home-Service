package ir.maktabsharif.final_project_taha_badri;

import org.springframework.boot.SpringApplication;

public class TestFinalProjectTahaBadriApplication {

    public static void main(String[] args) {
        SpringApplication.from(FinalProjectTahaBadriApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
