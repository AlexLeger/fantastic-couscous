package fantasticcouscous.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

/* This test starts the whole application and pretends to send an HTTP request (using restTemplate ?) then asserts the response */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {


    @LocalServerPort
    private int port;
    /* Note the use of webEnvironment=RANDOM_PORT to start the server with a random port (useful to avoid conflicts in test environments),
    and the injection of the port with @LocalServerPort. */

    private String endpoint = "business";

    @Autowired
    private TestRestTemplate restTemplate; //TestRestTemplate is provided by SpringBoot, we just have to autowire it.


    @Test
    public void businessServiceShouldReturnServiceInfo() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/service_info/",
                String.class)).contains("This is business-service");
    }

    /*@Test
    public void businessServiceShouldReturnBusinessInfo() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + endpoint,
                String.class)).contains("{\"login\":\"jmcclane\",\"firstName\":\"John\"}");
    }

    public void businessServiceShouldReturnBusinessInfo() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/" + endpoint,
                String.class)).contains("success");
    }*/



}