package fantasticcouscous.users;


//import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
//import org.springframework.messaging.converter.AbstractMessageConverter;

import java.util.Arrays;

@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

/*
    //https://spring.io/guides/gs/spring-boot/
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans in the current ApplicationContext ");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

            System.out.println("Let's inspect beans for org.springframework.amqp.support.converter.AbstractMessageConverter ");
            ctx.getBeanNamesForType(org.springframework.amqp.support.converter.AbstractMessageConverter.class);

            System.out.println("Let's inspect beans for org.springframework.messaging.converter.AbstractMessageConverter ");
            ctx.getBeanNamesForType(org.springframework.messaging.converter.AbstractMessageConverter.class);

        };
    }*/

}