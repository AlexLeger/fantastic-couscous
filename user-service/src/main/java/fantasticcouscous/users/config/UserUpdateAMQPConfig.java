package fantasticcouscous.users.config;

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

    @Profile("receiver")
    @Bean
    public UserUpdateReceiver receiver() {
        return new UserUpdateReceiver();
    }

    @Bean
    public UserUpdateSender sender() {
        return new UserUpdateSender();
    }
}
