package fantasticcouscous.business.config;

import fantasticcouscous.business.services.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(queues = "userupdate_queue")
@Slf4j
public class UserUpdateReceiver {

    @Autowired
    BusinessService businessService;

    @RabbitHandler
    public void receive(String login) {
        log.info(" [x] Received updated event  '" + login + "'");
        this.businessService.clearUserCache(login);
    }

}
