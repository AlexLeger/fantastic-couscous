package fantasticcouscous.users;

import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDataInit {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void initRepositoryWithAFewUsers(){ //TODO Read from config file
        List<UserData> users = new ArrayList<>();
        users.add(new UserData("kgodel","Kurt"));
        users.add(new UserData("kwallander","Kurt"));
        users.add(new UserData("lwallander","Linda"));
        users.add(new UserData("jmcclane","John"));
        users.add(new UserData("hgruber","Hans"));
        users.add(new UserData("sgruber","Simon"));
        userRepository.saveAll(users);
    }

}
