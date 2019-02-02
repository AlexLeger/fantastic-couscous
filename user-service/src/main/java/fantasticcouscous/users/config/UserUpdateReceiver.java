package fantasticcouscous.users.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "userupdate_queue")
@Slf4j
public class UserUpdateReceiver {

    @RabbitHandler
    public void receive(String in) {
        log.info(" [x] Received '" + in + "'");
    }

}
