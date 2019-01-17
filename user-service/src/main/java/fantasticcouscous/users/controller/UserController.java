package fantasticcouscous.users.controller;

import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


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
        return userRepository.findOneByLogin(login);
    }

    @GetMapping(value = "/service_info",
            produces = { "application/json" })
    public String getServiceInfo() {
        return "This is "+applicationName;
    }

}
