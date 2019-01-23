package fantasticcouscous.business.services;

import fantasticcouscous.business.UserServiceProxy;
import fantasticcouscous.business.foreign.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BusinessService {

    private Map<String,UserData> cache = new HashMap<>();

    @Autowired
    UserServiceProxy userServiceProxy;

    public BusinessService(UserServiceProxy userServiceProxy) { //Added for injection in BusinessServiceShould //TODO Maybe it's not how it should be done
        this.userServiceProxy = userServiceProxy;
    }

    /* In the future, this class will provide the services depending on the calling user's permissions.
    For now it will just call the user service to get the user's information. */
    public String performBusinessOperation(String login){
        UserData userData;
        log.info("BusinessOperation must be performed for login : {}", login);
        if(cache.containsKey(login)){
            log.info("Retrieving user data from cache for login : {}",login);
            userData = cache.get(login);
        } else {
            log.info("Retrieving user data from userServiceProxy for login : {}",login);
            userData = userServiceProxy.getUserInfo(login);
            cache.put(login,userData);
        }
        log.info("Retrieved user data  : {}",userData);
        return "Business operation is performed. Retrieved data for user : " + userData.toString();
    }

}