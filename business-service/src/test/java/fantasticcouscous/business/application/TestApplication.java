package fantasticcouscous.business.application;

import fantasticcouscous.business.UserServiceProxy;
import fantasticcouscous.business.services.BusinessService;
import feign.Feign;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import feign.mock.HttpMethod;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.lang.reflect.Type;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootApplication
@Import({BusinessService.class})
public class TestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TestApplication.class, args);
        assert (ctx != null);
    }

    //TODO Find out what the AssertionDecoder from the example is for (try leaving it out).
    //TODO If it turns out it really is necessary, switch it to Jackson instead of Gson
    class AssertionDecoder implements Decoder {

        private final Decoder delegate;

        public AssertionDecoder(Decoder delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object decode(Response response, Type type)
                throws IOException, DecodeException, FeignException {
            //assertThat(response.request(), notNullValue()); //import static org.hamcrest.Matchers.
            assertThat(response.request()).isNotNull(); //TODO NotNull vs isNotNull
            return delegate.decode(response, type);
        }

    }

    @Bean
    @Primary
    public UserServiceProxy mockUserServiceProxy(MockClient mockClient) {

        //Build Feign with MockClient
        UserServiceProxy mockUserServiceProxy = Feign.builder()
                .decoder(new AssertionDecoder(new JacksonDecoder()))
                //If removed we get feign.codec.DecodeException: class fantasticcouscous.business.foreign.UserData is not a type supported by this decoder.
                .client(mockClient)
                .contract(new SpringMvcContract())
                /* Got : java.lang.IllegalStateException: Method getServiceInfo not annotated with HTTP method type (ex. GET, POST)
                Found : https://github.com/spring-cloud/spring-cloud-netflix/issues/760 */
                .target(new MockTarget<>(UserServiceProxy.class));
        //TODO Look at other examples in MockClientTest

        return mockUserServiceProxy;
    }

    @Bean
    public MockClient mockClient(){
        //Create MockClient and set behaviour (which requests are accepted and with which response)
        MockClient mockClient = new MockClient();
        mockClient = mockClient
                .ok(HttpMethod.GET, "/user/jmcclane", "{\"login\": \"jmcclane\", \"firstName\": \"John\" }")
                .ok(HttpMethod.GET, "/user/hgruber", "{\"login\": \"hgruber\", \"firstName\": \"Hans\" }")
                .ok(HttpMethod.GET, "/user/hgruber", "{\"login\": \"sgruber\", \"firstName\": \"Simon\" }")
                .ok(HttpMethod.GET, "/service_info", "{\"applicationName\" : \"" + "user-service" + "\"}");
        //TODO Move MockClient behaviour configuration to test setup

        return mockClient;
    }

}
