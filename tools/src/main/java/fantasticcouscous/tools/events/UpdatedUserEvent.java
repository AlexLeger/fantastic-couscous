package fantasticcouscous.tools.events;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class UpdatedUserEvent extends RemoteApplicationEvent {

    private String login;

    protected UpdatedUserEvent() {
    }

    public UpdatedUserEvent(Object source, String originService, String name) {
        super(source, originService);
        this.login = name;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String name) {
        this.login = name;
    }


}
