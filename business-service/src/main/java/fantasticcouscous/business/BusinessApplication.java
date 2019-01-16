package fantasticcouscous.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusinessApplication {

    @Value("${spring.application.name}")
    private String applicationName;


    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }
}