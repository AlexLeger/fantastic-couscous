package fantasticcouscous.users.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

@Slf4j
public class UpdatedUserEvent extends RemoteApplicationEvent {

    private String updatedUserLogin;

    // Must supply a default constructor and getters/setters for deserialization
    public UpdatedUserEvent() {
    }

    public UpdatedUserEvent(Object source, String originService, String login) {
        // source is the object that is publishing the event
        // originService is the unique context ID of the publisher
        super(source, originService);
        this.updatedUserLogin = login;
        log.info("Updated user event was created for : {}"+login);
    }

    public String getUpdatedUserLogin() {
        return updatedUserLogin;
    }

    public UpdatedUserEvent setUpdatedUserLogin(String login) {
        this.updatedUserLogin = login;
        return this;
    }
}

// Source : https://tndavidson.github.io/blog/2017/06/01/custom-events-with-spring-cloud-bus/
