package fantasticcouscous.business.services;

import fantasticcouscous.business.UserServiceProxy;
import fantasticcouscous.business.foreign.UserData;
import feign.Feign;
import feign.FeignException;
import feign.Request;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.mock.HttpMethod;
import feign.mock.MockClient;
import feign.mock.MockTarget;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

@Slf4j
public class BusinessServiceShould {

    private BusinessService businessService;
    private MockClient mockClient;

    //TODO Find out what the AssertionDecoder from the example is for (try leaving it out)
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

    @BeforeEach
    public void setup(){

            //Create MockClient and set behaviour (which requests are accepted and with which response)
            mockClient = new MockClient();
            mockClient = mockClient
                    .ok(HttpMethod.GET, "/user/jmcclane",   "{\"login\": \"jmcclane\", \"firstName\": \"John\" }")
                    .ok(HttpMethod.GET, "/user/hgruber", "{\"login\": \"hgruber\", \"firstName\": \"Hans\" }")
                    .ok(HttpMethod.GET, "/user/hgruber", "{\"login\": \"sgruber\", \"firstName\": \"Simon\" }")
                    .ok(HttpMethod.GET, "/service_info", "{\"applicationName\" : \""+"user-service"+"\"}");
            //Build Feign with MockClient
            UserServiceProxy userServiceProxy = Feign.builder()
                    .decoder(new AssertionDecoder(new GsonDecoder()))
                    .client(mockClient)
                    .contract(new SpringMvcContract())
                    /* Got : java.lang.IllegalStateException: Method getServiceInfo not annotated with HTTP method type (ex. GET, POST)
                    Found : https://github.com/spring-cloud/spring-cloud-netflix/issues/760 */
                    .target(new MockTarget<>(UserServiceProxy.class));

                    //Inject fake userServiceProxy into businessService
                    businessService = new BusinessService(userServiceProxy);
                    //TODO Look at other examples in MockClientTest
    }

    @Test
    public void hitMock(){
    String login ="jmcclane";
        String result = businessService.performBusinessOperation(login);
        /*assertThat(result,notNullValue());
        assertThat(result.getLogin(),equals(login));
       assertThat(result.getFirstName(),equals("John"));*/
        assertThat(result).contains(login);
        mockClient.verifyStatus(); //TODO Find out what that's for
    }

    @Test
    public void hitCache(){
        String login ="jmcclane";

        //First call
        String result1 = businessService.performBusinessOperation(login);

        //Second call
        String result2 = businessService.performBusinessOperation(login);
        assertThat(result1).isEqualTo(result2);

        List<Request> results = mockClient.verifyTimes(HttpMethod.GET, "/user/"+login, 1); //TODO Understand why the expected number of times has to be set twice
        log.info("Test cache : {}",results.toString());
        MatcherAssert.assertThat(results, hasSize(1));
        mockClient.verifyStatus();
    }
}
