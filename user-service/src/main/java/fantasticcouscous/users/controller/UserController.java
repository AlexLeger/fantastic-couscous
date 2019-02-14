package fantasticcouscous.users.controller;

import fantasticcouscous.tools.events.UpdatedUserEvent;
import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@SpringBootConfiguration
//@EnableAutoConfiguration //It's already done by SpringBootConfiguration
public class UserController {

    @Value("${spring.application.name}")
    private String applicationName;


    @Autowired
    private ServiceMatcher busServiceMatcher;

    @Autowired
    private ApplicationEventPublisher publisher;

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
        this.publisher.publishEvent(new UpdatedUserEvent(this,this.busServiceMatcher.getServiceId(),login));

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

    @Getter
    private ArrayList<String> updatedUserList = new ArrayList<>();

    @EventListener
    public void handleUpdatedUserEvent(UpdatedUserEvent event) { //To unit-test handleUpdateUserEvent
        this.updatedUserList.add(event.getLogin());
        log.info("Event was received for login "+event.getLogin()+
                " with id "+event.getId()+
                " by source "+event.getSource()+
                " from originService "+event.getOriginService()+"" +
                " with destinationService "+event.getDestinationService());
    }

    //TODO Handle user deletion (and fire event too)
}

/* When using Lombok, to build or run tests in intelliJ we need :
Intellij Idea -> Preferences -> Build, Execution, Deployment -> Compiler -> Annotation Processors,
check the checkbox of "Enable annotation processing".
File -> Other Settings -> Default Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors,
check the checkbox of "Enable annotation processing".*/