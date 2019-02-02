package fantasticcouscous.business.controller;

import fantasticcouscous.business.foreign.MyCustomRemoteEvent;
import fantasticcouscous.business.services.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class BusinessController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private ApplicationContext applicationContext;

    public BusinessController(BusinessService businessService, ApplicationContext context) {

        this.businessService = businessService;
        this.applicationContext = context; //TODO Find out what that's for
    }

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

    @RequestMapping(value="/publish", method = RequestMethod.POST)
    public String publish() {
        final String myUniqueId = applicationContext.getId(); // each service instance must have a unique context ID

        final MyCustomRemoteEvent event =
                new MyCustomRemoteEvent(this, myUniqueId, "hello world");

        log.info("publish endpoint was called.");

        applicationContext.publishEvent(event);

        return "event published";
    }

}