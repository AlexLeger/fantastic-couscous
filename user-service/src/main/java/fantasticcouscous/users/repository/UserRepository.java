package fantasticcouscous.users.repository;

import fantasticcouscous.users.model.UserData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {

    public UserData getUserDatabyLogin(String login){

        UserData user;

        switch(login) {

            case "jmcclane" :
                user = new UserData();
                user.setLogin("jmcclane");
                user.setFirstName("John");
                /*user.setLastName("McClane");
                List<String> groups = new ArrayList<>();
                groups.add("user");
                groups.add("good guys");
                user.setGroups(groups);*/
                return user;

            case "GrosGros" :
                user = new UserData();
                user.setLogin("GrosGros");
                user.setFirstName("Mathias");
                /*user.setLastName("McClane");
                List<String> groups = new ArrayList<>();
                groups.add("user");
                groups.add("good guys");
                user.setGroups(groups);*/
                return user;
            default :
                return null;
        }
    }
}
