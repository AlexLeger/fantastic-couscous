package fantasticcouscous.business.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class UserUpdateAMQPConfig {

    @Bean
    public Exchange userUpdateExchange() { return new FanoutExchange("userupdate_exchange",true,true);
    }

    @Bean
    public Binding userUpdateBinding(FanoutExchange fanout,
                                     Queue queue) {
        return BindingBuilder.bind(queue).to(fanout);
    }

    @Bean
    public Queue userUpdateQueue() {
        return new Queue("userupdate_queue");
    }

    @Bean
    public UserUpdateReceiver receiver() {
        return new UserUpdateReceiver();
    }

}
