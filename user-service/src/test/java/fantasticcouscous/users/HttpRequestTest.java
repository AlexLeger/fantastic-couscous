package fantasticcouscous.users;

import fantasticcouscous.users.controller.UserController;
import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

/* This test starts the whole application and pretends to send an HTTP request (using restTemplate ?) then asserts the response */

//@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;
    /* Note the use of webEnvironment=RANDOM_PORT to start the server with a random port (useful to avoid conflicts in test environments),
    and the injection of the port with @LocalServerPort. */

    private String endpoint = "user";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private TestRestTemplate restTemplate; //TestRestTemplate is provided by SpringBoot, we just have to autowire it.

    @BeforeEach
    public void init_userRepository(){
        //Adds a few users to the repository for userServiceShouldReturnUserData test
        userRepository.save(new UserData("jmcclane", "John"));
        userRepository.save(new UserData("sgruber", "Simon"));
    }

    @Test
    public void userRepositoryShouldNotBeNull(){
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void setUserRepositoryShouldBeInitWithUsers(){
        log.info("userRepository contains "+userRepository.count()+ " users"); //TODO Find out why this isn't failing (as UserDataInit component should be completed)
        assertThat(userRepository.count()).isEqualTo(2);
    }

    @Test
    public void userServiceShouldReturnServiceInfo() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/service_info/",
                String.class)).contains("{\"applicationName\" : \"user-service\"}");
    }

    @Test
    public void userServiceShouldReturnUserData() throws Exception {
        String login = "jmcclane";
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + endpoint + "/" + login,
                String.class)).contains("{\"login\":\"jmcclane\",\"firstName\":\"John\"}");

        login = "sgruber";
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + endpoint + "/" + login,
                String.class)).contains("{\"login\":\"sgruber\",\"firstName\":\"Simon\"}");
    }

    @Test
    public void userServiceShouldUpdateUserData() throws Exception {
        String login = "jmcclane";
        UserData updatedUser = new UserData("jmcclane", "Holly");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<UserData> update = new HttpEntity<>(updatedUser,httpHeaders);
        this.restTemplate.put("http://localhost:" + port + "/" + endpoint + "/" + login,update);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + endpoint + "/" + login,
                String.class)).contains("{\"login\":\"jmcclane\",\"firstName\":\"Holly\"}");

        //TODO Make sure that user count hasn't changed
    }

    @Test
    public void userServiceShouldCreateUserData() throws Exception {
        UserData newUser = new UserData("hgruber", "Hans");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<UserData> update = new HttpEntity<>(newUser,httpHeaders);
        this.restTemplate.postForLocation("http://localhost:" + port + "/" + endpoint,update);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + endpoint + "/" + newUser.getLogin(),
                String.class)).contains("{\"login\":\"hgruber\",\"firstName\":\"Hans\"}");

        //TODO Make sure that user count has increased
    }

    @Test
    public void userServiceShouldSendEventOnUpdate(){
        String login = "jmcclane";
        UserData updatedUser = new UserData("jmcclane", "Holly");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<UserData> update = new HttpEntity<>(updatedUser,httpHeaders);
        this.restTemplate.put("http://localhost:" + port + "/" + endpoint + "/" + login,update);

        assertThat(this.userController.getUpdatedUserList()).contains(login);
    }


    @AfterEach
    public void clean() { userRepository.deleteAll(); }
}