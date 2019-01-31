package fantasticcouscous.users;

import org.springframework.stereotype.Component;

@Component
public class UserUpdateReceiver {

    public void receiveUpdate(String message) {
        System.out.println("Received <" + message + ">");
    }
}
