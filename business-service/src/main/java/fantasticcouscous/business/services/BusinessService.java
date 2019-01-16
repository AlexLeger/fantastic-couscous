package fantasticcouscous.business.services;

import org.springframework.stereotype.Component;

@Component
public class BusinessService {

    /* In the future, this class will provide the service depending on the calling user's permissions.
    For now it will just call the user service to get the user's information. */

    public String performBusinessOperation(){
        return "Business operation is performed.";
    }

}
