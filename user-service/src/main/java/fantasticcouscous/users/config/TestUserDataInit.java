package fantasticcouscous.users.config;

import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Profile("test")
public class TestUserDataInit implements UserDataInit {

    //For tests, we don't want to add any users to the repository

}
