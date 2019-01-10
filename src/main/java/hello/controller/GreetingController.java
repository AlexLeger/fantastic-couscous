package hello.controller;

import java.util.concurrent.atomic.AtomicLong;

import hello.model.GreetingData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public GreetingData greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new GreetingData(counter.incrementAndGet(),
                String.format(template, name));
    }
}
