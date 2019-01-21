package fantasticcouscous.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import fantasticcouscous.business.services.BusinessService;

@RestController
public class BusinessController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private BusinessService businessService;

    @GetMapping(value = "/service_info",
            produces = { "application/json" })
    public String getServiceInfo() {
        return "This is "+applicationName;
    }

    @GetMapping(value = "/business/{login}",
            produces = { "application/json" })
    public String getBusinessInfo(@PathVariable("login") String login) {
        return businessService.performBusinessOperation(login);
    }

    @GetMapping(value = "/call_myself",
            produces = { "application/json" })
    public String getHealthInfo() {
        return businessService.getServiceInfo();
    }

}
