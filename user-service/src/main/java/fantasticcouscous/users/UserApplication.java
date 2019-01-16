package fantasticcouscous.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    /* Removed CommandLineRunner because WebLayerTest failed with
    Caused by: java.lang.IllegalStateException: Failed to execute CommandLineRunner */

}