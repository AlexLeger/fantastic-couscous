package fantasticcouscous.business.controller;

import fantasticcouscous.business.services.BusinessService;
import fantasticcouscous.tools.events.UpdatedUserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@SpringBootConfiguration
public class BusinessController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private BusinessService businessService;

    @GetMapping(value = "/service_info",
            produces = { "application/json" })
    public String getServiceInfo() {
        return "{\"applicationName\" : \""+applicationName+"\"}";
    }

    @GetMapping(value = "/business/{login}",
            produces = { "application/json" })
    public String getBusinessInfo(@PathVariable("login") String login) {
        return businessService.performBusinessOperation(login);
    }

    @GetMapping(value = "/clear/{login}",
            produces = { "application/json" })
    public String clearBusinessUserCache(@PathVariable("login") String login) {
        businessService.clearCacheForUser(login);
        return "Cache was cleared for "+login;
    }

    @EventListener
    public void handleUserUpdate(UpdatedUserEvent event) {
        businessService.clearCacheForUser(event.getLogin());
        log.info("Event was received for login "+event.getLogin()+
                " with id "+event.getId()+
                " by source "+event.getSource()+
                " from originService "+event.getOriginService()+"" +
                " with destinationService "+event.getDestinationService());
    }

}