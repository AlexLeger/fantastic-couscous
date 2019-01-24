package fantasticcouscous.business;

import fantasticcouscous.business.foreign.UserData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Edgware-Release-Notes#renamed-starters

@FeignClient(name="user-service", primary = false)//Service id of User  //TODO Maybe that's an issue for production (there might be a reason why the default is primary = true for FeignClients. See if we can use @Qualifier instead ?
public interface UserServiceProxy {

    @GetMapping(value = "/user/{login}")
    public UserData getUserInfo(@PathVariable("login") String login);

    @GetMapping(value = "/service_info")
    public String getServiceInfo();

}
