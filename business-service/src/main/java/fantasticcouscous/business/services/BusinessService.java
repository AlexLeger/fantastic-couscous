package fantasticcouscous.business.services;

import fantasticcouscous.business.UserServiceProxy;
import fantasticcouscous.business.foreign.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
@Slf4j
public class BusinessService {

    @Autowired
    UserServiceProxy userServiceProxy;

    /* In the future, this class will provide the services depending on the calling user's permissions.
    For now it will just call the user service to get the user's information. */

    @Cacheable(value = "user", key = "{#login}")
    public String performBusinessOperation(String login){
        log.info("Retrieving user data from userServiceProxy for login : {}",login);
        UserData userData = userServiceProxy.getUserInfo(login);
        log.info("Retrieved user data  : {}",userData);
        return "Business operation is performed. Retrieved data for user : " + userData.toString();
    }

    @CacheEvict(value = "user", key = "{#login}")
    public void clearCacheForUser(String login){

    }

    @CacheEvict(value = "user", allEntries = true)
    public void clearCacheForAllUsers(){

    }

}