package fantasticcouscous.users.config;

import org.springframework.context.annotation.Profile;

@Profile("test")
public class TestUserDataInit implements UserDataInit {

    //For tests, we don't want to add any users to the repository

}
