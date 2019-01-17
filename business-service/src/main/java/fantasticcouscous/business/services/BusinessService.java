package fantasticcouscous.business.services;

import fantasticcouscous.business.BusinessApplication;
import fantasticcouscous.business.foreign.Quote;
import fantasticcouscous.business.foreign.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BusinessService {

    private static final Logger log = LoggerFactory.getLogger(BusinessApplication.class);

    /* In the future, this class will provide the service depending on the calling user's permissions.
    For now it will just call the user service to get the user's information. */

    @Value("${user-service.port}")
    private String userServicePort;

    private RestTemplate restTemplate;

    public String performBusinessOperation(){
        /*log.info("Retrieving user data from : "+"localhost:"+userServicePort+"/user/jmcclane");
        UserData userData = restTemplate.getForObject("localhost:"+userServicePort+"/user/jmcclane", UserData.class);
        log.info("Retrieved user data  : "+userData.toString());
        return "Business operation is performed. Retrieved data for user : " + userData.toString();*/
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        return "Business operation is performed. Retrieved quote : "+quote.toString();
    }

}
