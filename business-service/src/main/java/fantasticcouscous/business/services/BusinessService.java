package fantasticcouscous.business.services;

import fantasticcouscous.business.BusinessApplication;
import fantasticcouscous.business.UserServiceProxy;
import fantasticcouscous.business.foreign.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableCaching
public class BusinessService {

    @Autowired
    UserServiceProxy userServiceProxy;

    private static final Logger log = LoggerFactory.getLogger(BusinessApplication.class);

    /* In the future, this class will provide the service depending on the calling user's permissions.
    For now it will just call the user service to get the user's information. */

    @Cacheable(value = "user", key = "{#login}")
    public String performBusinessOperation(String login){
        log.info("Retrieving user data from userServiceProxy for login : "+login);
        UserData userData = userServiceProxy.getUserInfo(login);
        log.info("Retrieved user data  : "+userData.toString());
        return "Business operation is performed. Retrieved data for user : " + userData.toString();
    }

}
