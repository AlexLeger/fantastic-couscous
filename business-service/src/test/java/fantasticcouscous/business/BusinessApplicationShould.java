package fantasticcouscous.business;
/* Had the following error message because no package was declared for this test class :
Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(classes=...) with your test

In OF sometimes there is @SpringBootTest(classes=UnitTestApplication.class), I guess so as to start an application with a "test" configuration
*/

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BusinessApplicationShould {

    @Value("${spring.application.name}")
    private String applicationName;

    @Test
    public void shouldReadApplicationPropertiesFile(){
        assertThat(applicationName).isEqualTo("business-service");
    }

}
