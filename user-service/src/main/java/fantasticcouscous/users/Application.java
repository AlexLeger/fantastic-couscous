package fantasticcouscous.users;

import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {
            // save a couple of users
            userRepository.save(new UserData("jmcclane", "John"));
            userRepository.save(new UserData("hgruber", "Hans"));


            // fetch all users
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            for (UserData user : userRepository.findAll()) {
                log.info(user.toString());
            }
            log.info("");

            // fetch an individual user by ID
            /*userRepository.findById(1L)
                    .ifPresent(customer -> {
                        log.info("Customer found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    });*/

            // fetch users by first name
            log.info("Customer found with findByFirstName('John'):");
            log.info("--------------------------------------------");
            userRepository.findByFirstName("John").forEach(user -> {
                log.info(user.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");
        };
    }
}