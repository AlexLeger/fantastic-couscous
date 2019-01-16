package fantasticcouscous.business.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessController {

    @Value("${spring.application.name}")
    private String applicationName;

    @RequestMapping(value = "/service_info")
    public String getServiceInfo() {
        return "This is "+applicationName;
    }

}
