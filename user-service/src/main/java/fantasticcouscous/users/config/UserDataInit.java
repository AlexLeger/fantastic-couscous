package fantasticcouscous.users.config;

import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("!test")
//For tests, we don't want to add any users to the repository
public class UserDataInit {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void initRepositoryWithAFewUsers(){ //TODO Read from config file
        List<UserData> users = new ArrayList<>();
        users.add(new UserData("achristie","Agatha"));
        users.add(new UserData("fvargas","Fred"));
        users.add(new UserData("hmankell","Henning"));
        userRepository.saveAll(users);
    }
}
