package fantasticcouscous.users.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserUpdateSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange userUpdateExchange;

    public void send(String login) {
        template.convertAndSend(userUpdateExchange.getName(), "", login);
        log.info(" [x] Sent update event on :'" + login + "'");
    }

}

