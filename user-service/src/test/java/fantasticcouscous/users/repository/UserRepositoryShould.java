package fantasticcouscous.users.repository;

import fantasticcouscous.users.model.UserData;
import org.h2.engine.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@SpringBootTest
/* Without it, Srping doesn't know it has to start the application context and we get : Unsatisfied dependency expressed through field 'userRepository' */
@RunWith(SpringRunner.class)
/* Without it, all tests fail with NP exception at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68) */
public class UserRepositoryShould {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        UserData u1, u2, u3;
        u1 = new UserData("kgodel","Kurt");
        u2 = new UserData("kwallander","Kurt");
        u3 = new UserData("lwallander","Linda");
        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void findByFirstName() {
        List<UserData> results = userRepository.findByFirstName("Kurt");
        assertThat(results).hasSize(2);

        results = userRepository.findByFirstName("Henning");
        assertThat(results).hasSize(0);
    }

    @Test
    public void findOneByLogin() {
        UserData result = userRepository.findOneByLogin("kwallander");
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("kwallander");
        assertThat(result.getFirstName()).isEqualTo("Kurt");
    }

    @Test
    public void persistUser() {
        UserData userData = new UserData("hmankell","Henning");
        userRepository.save(userData);
        assertThat(userRepository.count()).isEqualTo(4);
        UserData result = userRepository.findOneByLogin("hmankell");
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("hmankell");
        assertThat(result.getFirstName()).isEqualTo("Henning");
    }

}