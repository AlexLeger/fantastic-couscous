package fantasticcouscous.business.services;

import fantasticcouscous.business.BusinessApplication;
import fantasticcouscous.business.foreign.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Service
public class BusinessService {

    private RestTemplate restTemplate = new RestTemplate();

    private static final Logger log = LoggerFactory.getLogger(BusinessApplication.class);

    /* In the future, this class will provide the service depending on the calling user's permissions.
    For now it will just call the user service to get the user's information. */

    @Value("${user-service.port}")
    private String userServicePort;

    @Value("${server.port}")
    private int port;

    public String performBusinessOperation(){
        if(isNull(this.restTemplate)) {
            log.info("Rest template is null !!!!!!!!");
        }
        String result = this.restTemplate.getForObject("http://localhost:" + port + "/service_info/",
                String.class);
        return result;
    }


    public String retrieveUserData(){
        log.info("Retrieving user data from : "+"localhost:"+userServicePort+"/user/jmcclane");
        UserData userData = restTemplate.getForObject("localhost:"+userServicePort+"/user/jmcclane", UserData.class);
        log.info("Retrieved user data  : "+userData.toString());
        return "Business operation is performed. Retrieved data for user : " + userData.toString();
    }

}
