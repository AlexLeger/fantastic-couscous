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
    public void cacheShouldExpireAfter() throws InterruptedException {

        long ttl = 11; //Time to live in seconds

        String login ="jmcclane";

        //First call to populate cache
        businessService.performBusinessOperation(login);

        //Wait for cache to expire
        Thread.sleep(ttl*1000);

        //Second call should call actual method again
        businessService.performBusinessOperation(login);

        mockClient.verifyTimes(HttpMethod.GET, "/user/"+login, 2);

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

    /*
    @Test
    public void cacheShouldNotStoreNullValues(){
        String login ="userthatdoesntexist";

        //First call should return null
        String result = businessService.performBusinessOperation(login);
        assertThat(result).isNull();

        //Second call
        businessService.performBusinessOperation(login);

        //Mock client should be called twice because null result doesn't get cached
        mockClient.verifyTimes(HttpMethod.GET, "/user/"+login, 2);


    }
*/


}

