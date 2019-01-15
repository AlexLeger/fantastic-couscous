package fantasticcouscous.business.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessController {

    @RequestMapping(value = "/service_info")
    public String getServiceInfo() {
        return "This is the business service."; //TODO Replace by application property (or switch to Actuator)
    }

}
