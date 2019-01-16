package fantasticcouscous.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import fantasticcouscous.business.services.BusinessService;

@RestController
public class BusinessController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/service_info")
    public String getServiceInfo() {
        return "This is "+applicationName;
    }

    @RequestMapping(value = "/business", produces = { "application/json" }, method = RequestMethod.GET)
    public String getBusinessInfo() {
        return businessService.performBusinessOperation();
    }

}
