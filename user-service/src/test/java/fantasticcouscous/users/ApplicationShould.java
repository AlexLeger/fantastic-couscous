package fantasticcouscous.users;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
public class ApplicationShould {

    @Test
    public void contextLoads() throws Exception {
        //Makes sure that application context can be loaded
        //Was KO because class wasn't in correct package so had the following message :
        // java.lang.IllegalStateException: Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(classes=...) with your test
    }

    /*
Another useful approach is to not start the server at all, but test only the layer below that,
where Spring handles the incoming HTTP request and hands it off to your controller.
That way, almost the full stack is used, and your code will be called exactly the same way as if
it was processing a real HTTP request, but without the cost of starting the server.

In this test, the full Spring application context is started, but without the server.

Source : https://spring.io/guides/gs/testing-web/
*/
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/service_info")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("This is the user service.")));
    }

}