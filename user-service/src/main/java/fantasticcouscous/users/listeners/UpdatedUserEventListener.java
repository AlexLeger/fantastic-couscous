package fantasticcouscous.users.listeners;

import fantasticcouscous.users.event.UpdatedUserEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Getter
public class UpdatedUserEventListener implements ApplicationListener<UpdatedUserEvent> {

    private List<String> updatedUserList = new ArrayList<>();

    @Override
    public void onApplicationEvent(UpdatedUserEvent updatedUserEvent) {
        log.info("User service told us to clear cache for login : {}",updatedUserEvent.getUpdatedUserLogin());
        updatedUserList.add(updatedUserEvent.getUpdatedUserLogin());
    }

}
