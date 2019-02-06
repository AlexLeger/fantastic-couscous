package fantasticcouscous.users.config;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@RemoteApplicationEventScan(basePackages = "fantasticcouscous.tools.events")
public class BusConfiguration { //TODO Find out if there is a way to share it between services
    // According to documentation, this should register event with the deserializer
    // Source : https://cloud.spring.io/spring-cloud-bus/single/spring-cloud-bus.html#_registering_events_in_custom_packages
}