package fantasticcouscous.users.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserData {

    @Id
    private String login;
    private String firstName;
    //private String lastName;
    //private Set<String> groupSet;


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
}

// https://spring.io/guides/gs/accessing-data-jpa/