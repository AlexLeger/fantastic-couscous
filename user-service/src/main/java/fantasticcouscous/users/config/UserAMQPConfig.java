package fantasticcouscous.users.config;

import fantasticcouscous.users.UserUpdateReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAMQPConfig {

    static final String exchangeName = "userExchange"; //TODO Replace with amqp.aml file like in OF ?
    static final String queueName = "userQueue";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("user.update");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(UserUpdateReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveUpdate");
    }
    /* The bean defined in the listenerAdapter() method is registered as a message listener in the container defined in container().
    It will listen for messages on the "spring-boot" queue. Because the Receiver class is a POJO,
    it needs to be wrapped in the MessageListenerAdapter, where you specify it to invoke receiveMessage.
    Source : https://spring.io/guides/gs/messaging-rabbitmq/
    */
}
