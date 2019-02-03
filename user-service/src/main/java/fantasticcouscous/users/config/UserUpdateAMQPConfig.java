package fantasticcouscous.users.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class UserUpdateAMQPConfig {

    @Bean
    public FanoutExchange userUpdateExchange() { return new FanoutExchange("userupdate_exchange",true,true);
    }

    @Bean
    public Binding userUpdateBinding(FanoutExchange userUpdateExchange,
                                     Queue userUpdateQueue) {
        return BindingBuilder.bind(userUpdateQueue).to(userUpdateExchange);
    }

    /* Firstly, whenever we connect to Rabbit we need a fresh, empty queue.
    To do this we could create a queue with a random name, or, even better - let the server choose a random queue name for us.
    Secondly, once we disconnect the consumer the queue should be automatically deleted. To do this with the Spring AMQP client,
    we defined and AnonymousQueue, which creates a non-durable, exclusive, auto-delete queue with a generated name: */

    @Bean
    public Queue userUpdateQueue() {
        return new AnonymousQueue();
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

//Source : https://www.rabbitmq.com/tutorials/tutorial-three-spring-amqp.html