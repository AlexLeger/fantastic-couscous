package fantasticcouscous.users.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserUpdateSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    public void send(String login) {
        this.template.convertAndSend(queue.getName(), login);
        log.info(" [x] Sent update event on :'" + login + "'");
    }

}

