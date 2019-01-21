package fantasticcouscous.business.foreign;

import javax.persistence.Entity;
import javax.persistence.Id;

// TODO Rely on API/Client/Public exposed classes rather than internal user-service classes and find out how to declare dependency in build

@Entity
public class UserData {

    @Id
    private String login;
    private String firstName;
    
    protected UserData() {
    }

    public UserData(String login, String firstName) {
        this.login = login;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}

// https://spring.io/guides/gs/accessing-data-jpa/