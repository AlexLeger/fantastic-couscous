package fantasticcouscous.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

/* This test starts the whole application and pretends to send an HTTP request (using restTemplate ?) then asserts the response */

@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
//TestRestTemplate is only auto-configured when @SpringBootTest has been configured with a webEnvironment that means it starts the web container and listens for HTTP requests
//https://stackoverflow.com/questions/39213531/spring-boot-test-unable-to-inject-testresttemplate-and-mockmvc
//Otherwise you get error saying No qualifying bean of type 'org.springframework.boot.test.web.client.TestRestTemplate'
public class HttpRequestTest {

    @LocalServerPort
    private int port;
    /* Note the use of webEnvironment=RANDOM_PORT to start the server with a random port (useful to avoid conflicts in test environments),
    and the injection of the port with @LocalServerPort. */

    private String endpoint = "business";

    @Autowired
    private TestRestTemplate restTemplate; //TestRestTemplate is provided by SpringBoot, we just have to autowire it.

    @Test
    public void service_infoEndpointShouldReturnAppName() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/service_info/",
                String.class)).contains("This is business-service");
    }

    @Test
    public void businessEndpointShouldReturnBusinessOp() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + endpoint,
                String.class)).contains("Business operation is performed. Retrieved data for user : UserData{login='hmankell', firstName='Henning'}");
    }


    @Test
    public void call_myselfEndpointShouldReturnAppName() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/service_info/",
                String.class)).contains("This is business-service");
    }

}