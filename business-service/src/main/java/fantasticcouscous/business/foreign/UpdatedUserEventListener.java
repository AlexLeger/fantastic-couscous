package fantasticcouscous.business.foreign;

import fantasticcouscous.business.services.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdatedUserEventListener implements ApplicationListener<UpdatedUserEvent> {

    @Autowired
    BusinessService businessService;

    @Override
    public void onApplicationEvent(UpdatedUserEvent updatedUserEvent) {
        log.info("User service told us to clear cache for login : {}",updatedUserEvent.getUpdatedUserLogin());
        businessService.clearUserCache();
    }

}
