package fantasticcouscous.users;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/* This test starts the whole application and pretends to send an HTTP request (using restTemplate ?) then asserts the response */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;
    /* Note the use of webEnvironment=RANDOM_PORT to start the server with a random port (useful to avoid conflicts in test environments),
    and the injection of the port with @LocalServerPort. */

    private String endpoint = "user";

    @Autowired
    private TestRestTemplate restTemplate; //TestRestTemplate is provided by SpringBoot, we just have to autowire it.

    @Test
    public void userServiceShouldReturnServiceInfo() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/service_info/",
                String.class)).contains("This is the user service.");
    }

    @Test
    public void userServiceShouldReturnUserData() throws Exception {
        String login = "jmcclane";
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + endpoint + "/" + login, //TODO Fix to expect json response
                String.class)).contains("Hello World");
    }
}