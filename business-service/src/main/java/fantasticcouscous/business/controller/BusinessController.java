package fantasticcouscous.business.controller;

import fantasticcouscous.business.UserServiceProxy;
import fantasticcouscous.business.foreign.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@EnableCaching
@RestController
public class BusinessController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    UserServiceProxy userServiceProxy;

    @GetMapping(value = "/service_info",
            produces = { "application/json" })
    public String getServiceInfo() {
        return "This is "+applicationName;
    }

    @GetMapping(value = "/business/{login}",
            produces = { "application/json" })
    public String getBusinessInfo(@PathVariable("login") String login) {
        return performBusinessOperation(login);
    }

    @Cacheable(value = "user", key = "{#login}")
    public String performBusinessOperation(String login){
        log.info("Retrieving user data from userServiceProxy for login : {}",login);
        UserData userData = userServiceProxy.getUserInfo(login);
        log.info("Retrieved user data  : {}",userData);
        return "Business operation is performed. Retrieved data for user : " + userData.toString();
    }

}
