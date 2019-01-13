package fantasticcouscous.users;

import fantasticcouscous.users.controller.UserController;
import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.theInstance;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WebLayerTest {

    /*
    In the test from ApplicationTest (using @AutoConfigureMockMvc) the full Spring application context was started, but without the server. We can narrow down the tests to just the web layer by using @WebMvcTest.
    The test assertion is the same as in the previous case, but here Spring Boot is only instantiating the web layer, not the whole context.
    In an application with multiple controllers you can even ask for just one to be instantiated, using, for example @WebMvcTest(HomeController.class)

    Source: https://spring.io/guides/gs/testing-web/
     */

    /* 1rst fail :
    * WebLayerTest fails with the following message :
    Field userRepository in fantasticcouscous.users.controller.UserController required a bean of type 'fantasticcouscous.users.repository.UserRepository' that could not be found.
    The injection point has the following annotations:
	- @org.springframework.beans.factory.annotation.Autowired(required=true)

	Fixed by adding ({UserController.class, UserRepository.class}) to the @WebMvcTest annotation (to say repository should be created)
	or @Autowired(required = false) on top of repository in UserController

	-> Better : Create mock repository with @MockBean and Mockito
	*/
    //TODO Try again once architecture is more complex (gateway, feign, etc.) to see what exactly isn't started

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository mockUserRepository;



    @Test
    public void shouldReturnServiceInfo() throws Exception {
        this.mockMvc.perform(get("/service_info")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("This is the user service.")));
    }

    @Test
    public void shouldReturnUserData() throws Exception {
        String login = "jmcclane";
        UserData user = new UserData();
        user.setLogin("jmcclane");
        user.setFirstName("John");
        when(mockUserRepository.getUserDatabyLogin(login)).thenReturn(user); //Set expectations for mock repository

        this.mockMvc.perform(get("/user/"+login)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"login\":\"jmcclane\",\"firstName\":\"John\"}")));
    }
}
