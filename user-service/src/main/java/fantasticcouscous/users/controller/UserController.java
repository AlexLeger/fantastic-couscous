package fantasticcouscous.users.controller;

import fantasticcouscous.users.event.MyCustomRemoteEvent;
import fantasticcouscous.users.event.UpdatedUserEvent;
import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public UserController(ApplicationContext context) {
        this.applicationContext = context;
    } //TODO Find out what that's for

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

    @GetMapping(value = "/user",
            produces = { "application/json" })
    public List<UserData> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping(value = "/user/{login}",
            produces = { "application/json" })
    public UserData updateUserInfo(@PathVariable("login") String login, @RequestBody UserData userData){
        log.info("Update was called for {}",login);
        UserData result = userRepository.save(userData);

        //Fire event to tell Business service to clear cache for this user
        final String instanceUniqueId = applicationContext.getId(); // each service instance must have a unique context ID
        final UpdatedUserEvent updatedUserEvent = new UpdatedUserEvent(this, instanceUniqueId, login);
        applicationContext.publishEvent(updatedUserEvent);

        return userData;
    }

    @PostMapping(value = "/user",
            produces = { "application/json" })
    public UserData createUserInfo(@RequestBody UserData userData){
        log.info("Create was called with data {}", userData);
        UserData result = userRepository.save(userData);
        log.info("Result : {}",result);

        return userData;
    }


    @RequestMapping(value="/publish", method = RequestMethod.POST)
    public String publish(){
        final String myUniqueId = applicationContext.getId(); // each service instance must have a unique context ID

        final MyCustomRemoteEvent event =
                new MyCustomRemoteEvent(this, myUniqueId, "hello world");

        log.info("publish endpoint was called.");

        applicationContext.publishEvent(event);

        return "event published";
    }
    //TODO Handle user deletion (and fire event too)
}

/* When using Lombok, to build or run tests in intelliJ we need :
Intellij Idea -> Preferences -> Build, Execution, Deployment -> Compiler -> Annotation Processors,
check the checkbox of "Enable annotation processing".
File -> Other Settings -> Default Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors,
check the checkbox of "Enable annotation processing".*/