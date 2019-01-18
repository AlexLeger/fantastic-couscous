package fantasticcouscous.business.services;

import fantasticcouscous.business.BusinessApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BusinessServiceShould {

    private static final Logger log = LoggerFactory.getLogger(BusinessApplication.class);

    @Autowired
    BusinessService businessService;

    @Test
    void performBusinessOperationShouldReturnSuccess() { //Error because port variable doesn't get read from application.properties so URL is http://localhost/-1/service_info/
        assertThat(businessService).isNotNull();
        String result = businessService.performBusinessOperation();
        log.info("Result : " + result);
        assertThat(result).contains("success");
    }
}