package hello;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GreeterTest {

    private Greeter greeter;

    @Before
    public void setUp() throws Exception {
        greeter = new Greeter();
    }

    @Test
    public void sayHello_ShouldReturnMessage() {
        assertEquals("Hello world!", greeter.sayHello());
    }
}