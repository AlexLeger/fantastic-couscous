package fantasticcouscous.business.services;

import fantasticcouscous.business.application.TestApplication;
import feign.Request;
import feign.mock.HttpMethod;
import feign.mock.MockClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(classes = TestApplication.class)
public class BusinessServiceShould {

    @Autowired
    BusinessService businessService;

    @Autowired
    MockClient mockClient;

    @BeforeEach
    public void setup(){

    }

    @Test
    public void hitMock(){
    String login ="jmcclane";
        String result = businessService.performBusinessOperation(login);
        assertThat(result).isNotNull();
        assertThat(result).contains(login);
    }


    @Test
    public void hitCache(){
        String login ="jmcclane";

        //First call
        String result1 = businessService.performBusinessOperation(login);

        //Second call
        String result2 = businessService.performBusinessOperation(login);
        assertThat(result1).isEqualTo(result2);

        List<Request> results = mockClient.verifyTimes(HttpMethod.GET, "/user/"+login, 1); //TODO Understand why the expected number of times has to be checked twice (once in verify, once in results size)
        log.info("Test cache : {}",results.toString());
        assertThat(results).hasSize(1);
        //mockClient.verifyStatus(); //TODO find out what that's for
    }

    @Test
    public void firstCallDoesntHitCache(){
        String login ="jmcclane";
        businessService.performBusinessOperation(login);
        List<Request> results = mockClient.verifyTimes(HttpMethod.GET, "/user/"+login, 1);
        assertThat(results).hasSize(1);

        String login2 ="hgruber";
        businessService.performBusinessOperation(login2);
        results = mockClient.verifyTimes(HttpMethod.GET, "/user/"+login2, 1);
        assertThat(results).hasSize(1);

    }

}

