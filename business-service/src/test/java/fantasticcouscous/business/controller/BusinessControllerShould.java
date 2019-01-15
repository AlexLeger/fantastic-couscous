package fantasticcouscous.business.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BusinessControllerShould {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnServiceInfo() throws Exception {
        this.mockMvc.perform(get("/service_info")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("This is the business service.")));
    }


}