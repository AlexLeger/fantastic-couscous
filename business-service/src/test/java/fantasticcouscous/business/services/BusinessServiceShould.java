package fantasticcouscous.business.services;

import com.netflix.discovery.converters.Auto;
import fantasticcouscous.business.TestApplication;
import fantasticcouscous.business.UserServiceProxy;
import feign.Feign;
import feign.FeignException;
import feign.Request;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

@Slf4j
@SpringBootTest(classes = TestApplication.class)
public class BusinessServiceShould {

    @Autowired
    BusinessService businessService;

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

    }

    @Test
    public void hitMock(){
    String login ="jmcclane";
        String result = businessService.performBusinessOperation(login);
        /*assertThat(result,notNullVamockClientlue());
        assertThat(result.getLogin(),equals(login));
       assertThat(result.getFirstName(),equals("John"));*/
        assertThat(result).contains(login);
    }


    /*@Test
    public void hitCache(){
        String login ="jmcclane";

        //First call
        String result1 = businessService.performBusinessOperation(login);

        //Second call
        String result2 = businessService.performBusinessOperation(login);
        assertThat(result1).isEqualTo(result2);

        List<Request> results = businessService.userServiceProxy.verifyTimes(HttpMethod.GET, "/user/"+login, 1); //TODO Understand why the expected number of times has to be set twice
        log.info("Test cache : {}",results.toString());
        MatcherAssert.assertThat(results, hasSize(1));
        mockClient.verifyStatus();
    }*/
}
