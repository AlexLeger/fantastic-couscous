package fantasticcouscous.users.repository;

import fantasticcouscous.users.model.UserData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {

    public UserData getUserDatabyLogin(String login){

        UserData user = new UserData();
        user.setLogin(login);

        switch(login) {

            case "jmcclane" :
                user.setFirstName("John");
                /*user.setLastName("McClane");
                List<String> groups = new ArrayList<>();
                groups.add("user");
                groups.add("good guys");
                user.setGroups(groups);*/
                return user;

            case "hgruber" :
                user.setFirstName("Hans");
                /*user.setLastName("Gruber");
                List<String> groups = new ArrayList<>();
                groups.add("user");
                groups.add("bad guys");
                user.setGroups(groups);*/
                return user;

            default :
                user.setFirstName("unknown user"); //TODO Throw exception or handle error instead
                return user;

        }
    }
}
