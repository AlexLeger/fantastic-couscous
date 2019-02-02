package fantasticcouscous.business.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class UserUpdateAMQPConfig {

    @Bean
    public Queue userUpdateQueue() {
        return new Queue("userupdate_queue");
    }

    @Bean
    public UserUpdateReceiver receiver() {
        return new UserUpdateReceiver();
    }

}
