package fantasticcouscous.business.services;

import fantasticcouscous.business.application.TestApplication;
import feign.Request;
import feign.mock.HttpMethod;
import feign.mock.MockClient;
import lombok.extern.slf4j.Slf4j;
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
        businessService.clearUserCache();
        mockClient.resetRequests();
    }

    @Test
    public void objectsUnderTestAreNotNull(){
        assertThat(businessService).isNotNull();
        assertThat(mockClient).isNotNull();
    }

    @Test
    public void mockClientRequestsAreResetBeforeEachTest(){
        assertThat(mockClient.verifyTimes(HttpMethod.GET, "/users/"+"jmmclane",0)).isEmpty();
    }

    @Test
    public void shouldReturnCorrectUserData(){
        String login ="jmcclane";
        String result = businessService.performBusinessOperation(login);
        assertThat(result).isNotNull();
        assertThat(result).contains(login);
    }

    @Test
    public void shouldNotHitCacheForFirstCall(){
        String login ="jmcclane";
        businessService.performBusinessOperation(login);
        mockClient.verifyTimes(HttpMethod.GET, "/user/"+login, 1);
    }

    @Test
    public void shouldHitCacheAndReturnSameDataForSecondCall(){
        String login ="jmcclane";

        //First call
        String result1 = businessService.performBusinessOperation(login);

        //Second call
        String result2 = businessService.performBusinessOperation(login);
        assertThat(result1).isEqualTo(result2);

        List<Request> results = mockClient.verifyTimes(HttpMethod.GET, "/user/"+login, 1);
        //mockClient.verifyStatus(); //TODO find out what that's for
    }

    @Test
    public void cacheCanBeCleared(){
        String login ="jmcclane";

        //First call
        businessService.performBusinessOperation(login);

        //Clear cache
        businessService.clearUserCache();

        //Second call
        businessService.performBusinessOperation(login);

        mockClient.verifyTimes(HttpMethod.GET, "/user/"+login, 2);
    }

}

