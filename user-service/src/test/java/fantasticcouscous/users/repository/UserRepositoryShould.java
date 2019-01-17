package fantasticcouscous.users.repository;

import fantasticcouscous.users.UserApplication;
import fantasticcouscous.users.model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
/* Without it, Spring doesn't know it has to start the application context and we get : Unsatisfied dependency expressed through field 'userRepository' */
//@ExtendWith(SpringExtension.class) //SpringExtension integrates Junit5 with the Spring Framework -> Useless here, it's already included in @SpringBootTest */
//@RunWith(SpringRunner.class)
/* Without it, all tests fail with NP exception at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68) */
@ActiveProfiles("test")
public class UserRepositoryShould {

    private static final Logger log = LoggerFactory.getLogger(UserApplication.class);

    @Autowired
    private UserRepository userRepository;

    @BeforeEach //Was @BeforeAll but org.junit.platform.commons.JUnitException: @BeforeAll method XXX must be static unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS).
    public void setUp() throws Exception {
        UserData u1, u2, u3;
        u1 = new UserData("kgodel","Kurt");
        u2 = new UserData("kwallander","Kurt");
        u3 = new UserData("lwallander","Linda");
        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
    }

    @AfterEach
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
    public void deleteById() {
        log.info("UserRepo count : "+ userRepository.count());
        Long countBefore = userRepository.count();
        userRepository.deleteById("kwallander");
        assertThat(userRepository.count()).isEqualTo(countBefore-1);
        UserData result = userRepository.findOneByLogin("kwallander");
        assertThat(result).isNull();
        log.info("UserRepo count : "+ userRepository.count());
    }

    @Test
    public void findOneByLogin() {
        log.info("UserRepo count : "+ userRepository.count());
        UserData result = userRepository.findOneByLogin("kwallander");
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("kwallander");
        assertThat(result.getFirstName()).isEqualTo("Kurt");
    }

    @Test
    public void persistUser() {
        //Check user count before persisting new user
        Long countBefore = userRepository.count();
        //Check that user doesn't already exist in the repository
        assertThat(userRepository.findOneByLogin("hmankell")).isNull();
        UserData userData = new UserData("hmankell","Henning");
        userRepository.save(userData);
        assertThat(userRepository.count()).isEqualTo(countBefore+1);
        UserData result = userRepository.findOneByLogin("hmankell");
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("hmankell");
        assertThat(result.getFirstName()).isEqualTo("Henning");
    }

}