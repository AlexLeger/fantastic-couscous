package fantasticcouscous.users.controller;

import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private UserRepository userRepository;
    /* "Autowired" can be replaced with constructor (tests OK). TODO: Find out the differences (if any) between the two options
     */

    @GetMapping(value = "/user/{login}",
            produces = { "application/json" })
    public UserData getUserInfo(@PathVariable("login") String login) {
        log.info("getUserInfo was called for login {}",login);
        return userRepository.findOneByLogin(login);
    }

    @GetMapping(value = "/service_info",
            produces = { "application/json" })
    public String getServiceInfo() {
        return "{\"applicationName\" : \""+applicationName+"\"}";
    }

}

/* When using Lombok, to build or run tests in intelliJ we need :
Intellij Idea -> Preferences -> Build, Execution, Deployment -> Compiler -> Annotation Processors,
check the checkbox of "Enable annotation processing".
File -> Other Settings -> Default Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors,
check the checkbox of "Enable annotation processing".*/